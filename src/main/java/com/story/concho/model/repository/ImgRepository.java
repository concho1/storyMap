package com.story.concho.model.repository;

import com.story.concho.model.domain.Img;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

// CrudRepository<DTO, key>
public interface ImgRepository extends CrudRepository<Img, Integer> {
    boolean existsByNameAndEmail(String name, String email);
    List<Img> findImgsByEmail(String email);
    // @Query 어노테이션을 사용하여 커스텀 쿼리 정의

    @Query("SELECT i FROM Img i WHERE i.email = :email ORDER BY i.id DESC")
    Page<Img> findPageImgsByEmail(@Param("email") String email, Pageable pageable);

    long countByEmail(String email);

}
