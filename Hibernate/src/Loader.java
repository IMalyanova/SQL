import entities.HierarchicalTable;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import java.io.File;
import java.util.List;

public class Loader {

    private static SessionFactory sessionFactory;
    private static String gap = "";
    private static Integer previous = -1;

    public static void main(String[] args) {

        setUp();

        Session session = sessionFactory.openSession();
        session.beginTransaction();

        List<HierarchicalTable> hierarTables = (List<HierarchicalTable>) session.createQuery("FROM HierarchicalTable").list();

        for (HierarchicalTable element : hierarTables) {

            listDirectory(element.getParentId(), element.getName());
        }

        session.getTransaction().commit();
        session.close();

        //==================================================================
        if (sessionFactory != null) {

            sessionFactory.close();
        }
    }
       //=====================================================================

    private static void setUp() {

        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure(new File("src/config/hibernate.cfg.xml"))
                .build();
        try {

            sessionFactory = new MetadataSources(registry).buildMetadata().buildSessionFactory();

        } catch (Exception e) {

            StandardServiceRegistryBuilder.destroy(registry);
            throw e;
        }
    }


    private static void listDirectory(Integer parentId, String name) {

        if ( parentId == 0 ){
            gap ="";
            System.out.println(name);

        }else if ( parentId > previous ){
            gap = gap + "     ";
            System.out.println(gap + name);

        }else if (parentId == previous){
            System.out.println(gap + name);

        }else {
            gap = gap.substring(0, gap.length() - 5); //("     ","");
            System.out.println(gap + name);
        }

        previous = parentId;
    }



}
