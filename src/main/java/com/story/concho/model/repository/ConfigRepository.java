package com.story.concho.model.repository;

import com.story.concho.model.domain.Config;
import org.springframework.data.repository.CrudRepository;

public interface ConfigRepository extends CrudRepository<Config, String> {
}
