package br.com.adrianorodrigues.posterr.usecase.post.create.processors;

import java.util.List;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import br.com.adrianorodrigues.posterr.domain.Post;
import br.com.adrianorodrigues.posterr.exceptions.ResourceNotFoundException;

@Service
@RequiredArgsConstructor
public class CreatePostProcessorFactoryImpl implements CreatePostProcessorFactory{

	private final List<CreatePostProcessor> postProcessors;

	@Override
	public CreatePostProcessor getProcessor(Post post) {
		return postProcessors
				.stream()
				.filter( createPostProcessor ->  post.getType() == createPostProcessor.type())
				.findAny()
				.orElseThrow(() -> new ResourceNotFoundException( "createPostProcessor", post.getType().toString() ) );
	}
}
