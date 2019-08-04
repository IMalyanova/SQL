import entities.HierarchicalTable;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * Created by Danya on 26.10.2015.
 */
public class Loader {
    private static SessionFactory sessionFactory;
    public static String gap = "";
    private static Integer previous = 0;

    public static void main(String[] args) throws IOException {
        setUp();

        Session session = sessionFactory.openSession();
        session.beginTransaction();

        List<HierarchicalTable> hierarTables = (List<HierarchicalTable>) session.createQuery("FROM HierarchicalTable").list();
//        System.out.println("1. Вывести иерархический список:");
//        for (HierarchicalTable element : hierarTables) {
//            System.out.println(element.getParentId() + "  " + element.getName());
//        }

//        HierarchicalTable hierarchicalTable = new HierarchicalTable("Список");
//        session.save(hierarchicalTable);
//        System.out.println("Сохранен id = " + hierarchicalTable.getId());

//        Department dept = (Department) session.createQuery("FROM Department WHERE name=:name")
//            .setParameter("name", "Отдел производства").list().get(0);
//        session.delete(dept);

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
                .configure(new File("src/config/hibernate.cfg.xml")) // configures settings from hibernate.config.xml
                .build();
        try {
            sessionFactory = new MetadataSources(registry).buildMetadata().buildSessionFactory();
        } catch (Exception e) {
            // The registry would be destroyed by the SessionFactory, but we had trouble building the SessionFactory
            // so destroy it manually.
            StandardServiceRegistryBuilder.destroy(registry);
            throw e;
        }
    }


    private static void listDirectory(Integer parentId, String name) {

            if ( parentId == 0){
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

            Loader.previous = parentId;
    }






}
