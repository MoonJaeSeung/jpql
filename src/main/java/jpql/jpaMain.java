package jpql;

import javax.persistence.*;
import java.util.Collection;
import java.util.List;

public class jpaMain {

    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();
        //엔티티 매니저는 쓰레드간에 공유 X (사용하고 버려야 한다)

        EntityTransaction tx = em.getTransaction(); // jpa의 모든 데이터의 변경은 트랜잭션 안에서 실행
        tx.begin();

        try {

            Team team1 = new Team();
            team1.setName("team1");

            em.persist(team1);

//            Team team2 = new Team();
//            team2.setName("team1");
//
//            em.persist(team2);

            Member member1 = new Member();
            member1.setUsername("member1");
            member1.setAge(10);
            member1.setType(MemberType.ADMIN);

            member1.setTeam(team1);
            em.persist(member1);

            Member member2 = new Member();
            member2.setUsername("member2");
            member2.setAge(103);
            member2.setType(MemberType.ADMIN);

            member2.setTeam(team1);

            em.persist(member2);
            em.flush();
            em.clear();

            String query = "select m.username From Team t join t.members m";
            List<String> result = em.createQuery(query,String.class).getResultList();

            System.out.println("result = " + result);

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
            e.printStackTrace();
        } finally {
            em.close();
        }
        emf.close();
    }
}




















