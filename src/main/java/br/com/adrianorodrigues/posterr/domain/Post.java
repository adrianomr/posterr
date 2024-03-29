package br.com.adrianorodrigues.posterr.domain;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

import java.time.Instant;
import java.util.Arrays;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import br.com.adrianorodrigues.posterr.enums.PostType;
import br.com.adrianorodrigues.posterr.exceptions.DataValidationException;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "post")
public class Post {

	private static final Map<PostType, List<PostType>> invalidPostTypes = buildInvalidPostTypes();

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private UUID id;
	@Column
	private String content;
	@Column
	@Enumerated(EnumType.STRING)
	private PostType type;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "original_post_id")
	private Post originalPost;
	@Column
	private String originalPostContent;
	@Column
	private UUID originalPostUserId;
	@Column
	private UUID userId;
	@Column
	private Instant createdAt;

	private static Map<PostType, List<PostType>> buildInvalidPostTypes() {
		var invalids = new EnumMap<PostType, List<PostType>>( PostType.class );
		invalids.put( PostType.QUOTE, Arrays.asList( PostType.QUOTE ) );
		invalids.put( PostType.REPOST, Arrays.asList( PostType.REPOST ) );
		return invalids;
	}

	public void validate() {
		validateContentIsNotNull();
		validateContentSize();
		validatePostType();
	}

	public void validateOriginalPost() {
		validateOriginalUser();
		validateOriginalPostType();
	}

	public void validateRegularPost() {
		if (originalPost != null) {
			throw new DataValidationException( "originalPost", "Original post must be null" );
		}
	}

	public void addOriginalPost(UUID originalPostId) {
		if (nonNull( originalPostId )) {
			originalPost = Post.builder().id( originalPostId ).build();
		}
	}

	public void addOriginalPost(Post originalPost) {
		this.originalPostContent = originalPost.getContent();
		this.originalPostUserId = originalPost.getUserId();
		this.originalPost = originalPost;
	}

	private void validateOriginalUser() {
		if (Objects.equals( originalPost.getUserId(), userId )) {
			throw new DataValidationException( "originalPost.userId",
					"Original post user id has to be different from current user id" );
		}
	}

	private void validateOriginalPostType() {
		if (isOriginalPostTypeValid()) {
			throw new DataValidationException( "originalPost.type", "Post type cannot be: " + originalPost.getType() );
		}
	}

	private boolean isOriginalPostTypeValid() {
		List<PostType> invalidTypes = invalidPostTypes.get( type );
		return invalidTypes.stream().anyMatch( postType -> postType == originalPost.getType() );
	}

	private void validateContentSize() {
		if (content.length() > 777) {
			throw new DataValidationException( "content", "Max content size is 777" );
		}
	}

	private void validateContentIsNotNull() {
		if (isNull( content )) {
			throw new DataValidationException( "content", "Content cannot be null" );
		}
	}

	private void validatePostType() {
		if (isNull( type ))
			throw new DataValidationException( "type", "Type cannot be null" );
	}
}
