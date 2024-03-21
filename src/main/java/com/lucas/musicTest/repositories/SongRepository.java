package com.lucas.musicTest.repositories;


import com.lucas.musicTest.models.SongModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface SongRepository extends JpaRepository<SongModel, UUID> {

}
