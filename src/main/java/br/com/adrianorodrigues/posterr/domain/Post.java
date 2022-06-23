package br.com.adrianorodrigues.posterr.domain;

import static java.util.Objects.nonNull;

import java.util.Arrays;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
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
	@ManyToOne
	@JoinColumn(name = "original_post_id")
	private Post originalPost;

	private static Map<PostType, List<PostType>> buildInvalidPostTypes() {
		var invalids = new EnumMap<PostType, List<PostType>>( PostType.class );
		invalids.put( PostType.QUOTE, Arrays.asList( PostType.QUOTE ) );
		invalids.put( PostType.REPOST, Arrays.asList( PostType.REPOST ) );
		return invalids;
	}

	public void validateOriginalPost() {
		if (originalPost == null) {
			throw new DataValidationException( "originalPost", "Post must have a original post" );
		}
		validateOriginalPostType();
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
}
