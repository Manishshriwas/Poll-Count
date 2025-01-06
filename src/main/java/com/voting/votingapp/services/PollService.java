package com.voting.votingapp.services;


import com.voting.votingapp.model.Poll;
import com.voting.votingapp.repo.PollRepository;
import jakarta.transaction.Transactional;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class PollService {
    private final PollRepository pollRepository;

    public PollService(PollRepository pollRepository) {
        this.pollRepository = pollRepository;
    }

    public Poll createPoll(Poll poll) {
        return pollRepository.save(poll);
    }
    public List<Poll> getAllPolls(){
        return pollRepository.findAll();
    }

    public Optional<Poll> getPollById(Long id) {
        return pollRepository.findById(id);
    }


    public void vote(Long pollId, int optionIndex) {
        Poll poll = pollRepository.findById(pollId).orElseThrow(() ->
                new IllegalArgumentException("Poll with ID " + pollId + " not found"));

        // Step 2: Validate the option index
        if (optionIndex < 0 || optionIndex >= poll.getOptions().size()) {
            throw new IllegalArgumentException("Invalid option index: " + optionIndex);
        }

        // Step 3: Increment the vote count
        List<Long> votes = poll.getVotes();
        votes.set(optionIndex, votes.get(optionIndex) + 1);

        // Step 4: Save the updated poll back to the database
        pollRepository.save(poll);

    }
}

