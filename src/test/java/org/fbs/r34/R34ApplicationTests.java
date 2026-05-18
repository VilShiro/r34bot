package org.fbs.r34;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.utility.BotUtils;
import org.fbs.r34.provider.Rule34Provider;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class R34ApplicationTests {

    @Autowired
    private Rule34Provider rule34Provider;

    @Test
    void rateLimiterTest() {
        String someUpdate = "{update_id=916596497, message=null, edited_message=null, channel_post=null, edited_channel_post=null, business_connection=null, business_message=null, edited_business_message=null, deleted_business_messages=null, inline_query={id='3808625607025029087', from={id=886764751, is_bot=false, first_name='Rei', last_name='Ayanami', username='idontknowwhatyoumeanbro', language_code='be', is_premium='null', added_to_attachment_menu='null', can_join_groups=null, can_read_all_group_messages=null, supports_inline_queries=null, can_connect_to_business=null, has_main_web_app=null, has_topics_enabled=null, allows_users_to_create_topics=null}, location=null, query='dog', offset='', chat_type='sender'}, chosen_inline_result=null, callback_query=null, shipping_query=null, pre_checkout_query=null, poll=null, poll_answer=null, my_chat_member=null, chat_member=null, chat_join_request=null, message_reaction=null, message_reaction_count=null, chat_boost=null, removed_chat_boost=null, purchased_paid_media=null}";
        Update update = BotUtils.parseUpdate(someUpdate);
        long start = System.currentTimeMillis();
        for (int i = 0; i < 10; i++) {
            rule34Provider.getPhotos(update.inlineQuery());
        }
        long end = System.currentTimeMillis();
        IO.println("start: " + start);
        IO.println("end: " + end);
        Assertions.assertTrue(start + 9*1000 <= end);
    }

}
