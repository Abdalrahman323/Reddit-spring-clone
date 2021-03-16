package com.springredditclone.redditclone.service;

import com.springredditclone.redditclone.dao.entities.Subreddit;
import com.springredditclone.redditclone.dao.repository.SubredditRepository;
import com.springredditclone.redditclone.dto.SubredditDTO;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class SubredditService {


    final private SubredditRepository subredditRepository;

    @Transactional
    public SubredditDTO save(SubredditDTO subredditDTO){
        Subreddit savedSubreddit = subredditRepository.save(mapSubredditDto(subredditDTO));
        subredditDTO.setId(savedSubreddit.getId());

        return subredditDTO;
    }

    @Transactional
    public List<SubredditDTO> getAll() {

        return subredditRepository.findAll()
                .stream()
                .map(this::maptoDTO)
                .collect(Collectors.toList());
    }

    private SubredditDTO maptoDTO(Subreddit subreddit) {
        return SubredditDTO.builder().name(subreddit.getName())
                .description(subreddit.getDescription())
                .id(subreddit.getId())
                .build();
    }

    private Subreddit mapSubredditDto(SubredditDTO subredditDTO) {
        // use the buider pattern to construct our subreddit entity
        return Subreddit.builder().name(subredditDTO.getName())
                .description(subredditDTO.getDescription())
                .build();
    }


}
