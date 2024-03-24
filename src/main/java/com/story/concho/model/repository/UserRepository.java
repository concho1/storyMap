package com.story.concho.model.repository;

import com.story.concho.model.domain.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

/*
    기본 CRUD 연산 메서드
    save(S entity): 주어진 엔티티를 저장합니다. 새로운 엔티티의 추가 또는 기존 엔티티의 업데이트에 사용됩니다.
    findById(ID id): 주어진 ID에 해당하는 엔티티의 인스턴스를 검색합니다. 결과는 Optional<T>로 반환됩니다.
    existsById(ID id): 주어진 ID를 가진 엔티티의 존재 여부를 반환합니다.
    findAll(): 모든 엔티티를 리스트로 반환합니다.
    findAllById(Iterable<ID> ids): 주어진 ID 컬렉션에 해당하는 엔티티들을 검색합니다.
    count(): 저장소에 있는 엔티티의 총 개수를 반환합니다.
    deleteById(ID id): 주어진 ID를 가진 엔티티를 삭제합니다.
    delete(T entity): 주어진 엔티티를 삭제합니다.
    deleteAll(Iterable<? extends T> entities): 주어진 엔티티 컬렉션을 삭제합니다.
    deleteAll(): 저장소의 모든 엔티티를 삭제합니다.
 */
// 기본 키가 id
public interface UserRepository extends CrudRepository<User, String> {
    boolean existsByEmailAndPw(String email, String pw);

    @Query("SELECT u.nickname FROM User u WHERE u.email = ?1")
    String findNicknameByEmail(String email);

    @Query("SELECT u.folderId FROM User u WHERE u.email = ?1")
    String findFolderIdByEmail(String email);
}
