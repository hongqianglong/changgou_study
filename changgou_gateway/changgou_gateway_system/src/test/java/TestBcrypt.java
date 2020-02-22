import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import org.springframework.security.crypto.bcrypt.BCrypt;

import java.util.Date;

/**
 * @author hql
 * @date 2020-01-02 13:31
 */
public class TestBcrypt {
    public static void main(String[] args) {
/*        String gensalt = BCrypt.gensalt();
        System.out.println("得到的盐是"+gensalt);
        String hashpw = BCrypt.hashpw("123456", gensalt);

        System.out.println(hashpw);


        boolean checkpw = BCrypt.checkpw("123456", hashpw);
        System.out.println(checkpw);*/

/*
        JwtBuilder builder = Jwts.builder()
                .setId("888")
                .setSubject("小白")
                .setIssuedAt(new Date())
                .
*/
        //获取盐
        String gensalt = BCrypt.gensalt();
        System.out.println("得到的盐"+gensalt);




    }





}
