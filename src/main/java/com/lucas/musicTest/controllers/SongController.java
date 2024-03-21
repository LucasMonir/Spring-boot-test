package com.lucas.musicTest.controllers;

import com.lucas.musicTest.dtos.SongRecordDto;
import com.lucas.musicTest.models.SongModel;
import com.lucas.musicTest.repositories.SongRepository;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
public class SongController {

    @Autowired
    SongRepository songRepository;

    @PostMapping("/songs")
    public ResponseEntity<SongModel> saveSong(@RequestBody  @Valid SongRecordDto song){
        var songModel = new SongModel();
        BeanUtils.copyProperties(song, songModel);
        return ResponseEntity.status(HttpStatus.CREATED).body(songRepository.save(songModel));
    }

    @GetMapping("/songs")
    public ResponseEntity<List<SongModel>> getSongs(){
        List<SongModel> songs = songRepository.findAll();

        if(!songs.isEmpty()){
            for (SongModel song : songs){
                UUID id = song.getId();
                song.add(linkTo(methodOn(SongController.class).getSong(id)).withSelfRel());
            }
        }

        return ResponseEntity.status(HttpStatus.OK).body(songs);
    }

    @GetMapping("/songs/{id}")
    public ResponseEntity<Object> getSong(@PathVariable(value="id") UUID id){
        Optional<SongModel> song = songRepository.findById(id);

        if(song.isEmpty())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Song not found!");

        return ResponseEntity.status(HttpStatus.OK).body(song.get());
    }

    @PutMapping("/songs/{id}")
    public ResponseEntity<Object> updateSong(@PathVariable(value="id") UUID id, @RequestBody @Valid SongRecordDto songRecordDto){
        Optional<SongModel> song = songRepository.findById(id);

        if(song.isEmpty())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Song not found!");

        var songModel = song.get();
        BeanUtils.copyProperties(songRecordDto, songModel);
        return ResponseEntity.status(HttpStatus.OK).body(songRepository.save(songModel));
    }
}
