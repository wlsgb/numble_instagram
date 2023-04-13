package com.instagram.numble_instagram.repository.message;

import com.instagram.numble_instagram.model.entity.message.ChatRoom;
import com.instagram.numble_instagram.model.entity.message.UserChatRoomMapped;
import com.instagram.numble_instagram.model.entity.user.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserChatRoomMappedRepository extends JpaRepository<UserChatRoomMapped, Long> {
    List<UserChatRoomMapped> findAllByChatUser(User chatUser);
    List<UserChatRoomMapped> findAllByChatUserOrChatUser(User fromUser, User toUser);
    Optional<UserChatRoomMapped> findByChatRoomAndChatUser(ChatRoom chatRoom, User fromUser);
    List<UserChatRoomMapped> findAllByChatRoom(ChatRoom chatRoom);

    @Query("""
            SELECT UCRM
            FROM UserChatRoomMapped AS UCRM
            WHERE UCRM.chatRoom IN (SELECT IN_UCRM.chatRoom FROM UserChatRoomMapped AS IN_UCRM WHERE IN_UCRM.chatUser = :fromUser)
              AND UCRM.chatRoom IN (SELECT IN_UCRM.chatRoom FROM UserChatRoomMapped AS IN_UCRM WHERE IN_UCRM.chatUser = :toUser)
            """)
    List<UserChatRoomMapped> findChatRoom(@Param("fromUser") User fromUser, @Param("toUser") User toUser, Pageable pageable);

    @Query("""
            SELECT count (UCRM) > 0
            FROM UserChatRoomMapped AS UCRM
            WHERE UCRM.chatRoom IN (SELECT IN_UCRM.chatRoom FROM UserChatRoomMapped AS IN_UCRM WHERE IN_UCRM.chatUser = :fromUser)
              AND UCRM.chatRoom IN (SELECT IN_UCRM.chatRoom FROM UserChatRoomMapped AS IN_UCRM WHERE IN_UCRM.chatUser = :toUser)
            """)
    boolean existsChatRoom(@Param("fromUser") User fromUser, @Param("toUser") User toUser);
}
