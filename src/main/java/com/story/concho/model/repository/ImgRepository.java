package com.story.concho.model.repository;

import com.story.concho.model.domain.Img;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

// CrudRepository<DTO, key>
public interface ImgRepository extends CrudRepository<Img, Integer> {
    boolean existsByName(String name);

    List<Img> findImgsByEmail(String email);
}
