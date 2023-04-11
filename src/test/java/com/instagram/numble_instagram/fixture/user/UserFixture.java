package com.instagram.numble_instagram.fixture.user;

import com.instagram.numble_instagram.model.entity.user.User;

import org.jeasy.random.EasyRandom;
import org.jeasy.random.EasyRandomParameters;

import static org.jeasy.random.FieldPredicates.*;

public class UserFixture {

    public static User create(Long userId, String nickname) {
        var idPredicate = named("id")
                .and(ofType(Long.class))
                .and(inClass(User.class));

        var nicknamePredicate = named("nickname")
                .and(ofType(String.class))
                .and(inClass(User.class));

        var param = new EasyRandomParameters()
                .randomize(idPredicate, () -> userId)
                .randomize(nicknamePredicate, () -> nickname);

        return new EasyRandom(param).nextObject(User.class);
    }

    public static User create(String nickname) {
        var idPredicate = named("id")
                .and(ofType(Long.class))
                .and(inClass(User.class));

        var nicknamePredicate = named("nickname")
                .and(ofType(String.class))
                .and(inClass(User.class));

        var profileImageUrlPredicate = named("profileImageUrl")
                .and(ofType(String.class))
                .and(inClass(User.class));

        var param = new EasyRandomParameters()
                .randomize(nicknamePredicate, () -> nickname)
                .randomize(profileImageUrlPredicate, () -> "https://numble.file-store")
                .excludeField(idPredicate);

        return new EasyRandom(param).nextObject(User.class);
    }
}
