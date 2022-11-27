import jakarta.persistence.TypedQuery;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("co chcesz robic?(dodaj / lista / szukaj / usun / aktualizuj");
        String odpowiedz = scanner.nextLine();

        if(odpowiedz.equalsIgnoreCase("dodaj")){
            try (Session session = HibernateUtil.INSTANCE.getSessionFactory().openSession()){
                Transaction transaction = session.beginTransaction();
                System.out.println("podaj marke");
                String marka = scanner.nextLine();

                System.out.println("podaj moc");
                Double moc = Double.parseDouble(scanner.nextLine());

                System.out.println("podaj kolor");
                String kolor = scanner.nextLine();

                Integer rokProdukcji = null;
                do{
                    System.out.println("podaj rok produkcji");
                    rokProdukcji = Integer.parseInt(scanner.nextLine());
                }while (!(rokProdukcji >= 1990 && rokProdukcji <= 2020));

                System.out.println("czy jest elektryczny? (tak / nie)");
                Boolean elektryczny = false;
                String odpowiedzCzyElektryczny = scanner.nextLine();
                if(odpowiedzCzyElektryczny.equalsIgnoreCase("tak")){
                    elektryczny = true;
                } else if (odpowiedzCzyElektryczny.equalsIgnoreCase("nie")) {
                    elektryczny = false;
                }else {
                    System.out.println("niepoprawna odpowiedzi i nie jest elektryczny");
                    elektryczny = false;
                }

                Pojazd pojazd = Pojazd.builder()
                        .marka(marka)
                        .moc(moc)
                        .kolor(kolor)
                        .rokProdukcji(rokProdukcji)
                        .elektryczny(elektryczny)
                        .build();

                session.persist(pojazd);

                transaction.commit();


            }catch (Exception e){
                System.err.println("Błąd bazy: " + e);
            }
        } else if(odpowiedz.equalsIgnoreCase("lista")){
            try (Session session = HibernateUtil.INSTANCE.getSessionFactory().openSession()){
                TypedQuery<Pojazd> zapytanie = session.createQuery("FROM Pojazd", Pojazd.class);
                List<Pojazd> listaWszystkichPojazdow = zapytanie.getResultList();
                for (Pojazd pojazd : listaWszystkichPojazdow) {
                    System.out.println(pojazd);
                }
            }catch (Exception e){
                System.err.println("Błąd bazy: " + e);
            }
        } else if(odpowiedz.equalsIgnoreCase("szukaj")) {
            try (Session session = HibernateUtil.INSTANCE.getSessionFactory().openSession()) {
                TypedQuery<Pojazd> zapytanie = session.createQuery("FROM Pojazd", Pojazd.class);
                List<Pojazd> listaWszystkichStudentow = zapytanie.getResultList();

                System.out.println("podaj id pojazdu");
                String odpowiedzId = scanner.nextLine();
                long pojazdId = Long.parseLong(odpowiedzId);

                Pojazd pojazd = session.get(Pojazd.class, pojazdId);
                if (pojazd == null) {
                    System.err.println("Nie znaleziono pojazdu");
                } else {
                    System.out.println("Pojazd: " + pojazd);
                }
            }catch (Exception e){
                System.err.println("Błąd bazy: " + e);
            }
        }else if (odpowiedz.equalsIgnoreCase("usun")){
            try (Session session = HibernateUtil.INSTANCE.getSessionFactory().openSession()){
                Transaction transaction = session.beginTransaction();
                System.out.println("Podaj id pojazdu który chcesz usunać:");
                String id = scanner.nextLine();
                Long pojazdId = Long.parseLong(id);

                Pojazd pojazd = session.get(Pojazd.class, pojazdId);
                if (pojazd == null){
                    System.err.println("Pojazd nie istnieje.");
                } else {
                    session.remove(pojazd);
                }
                transaction.commit();
            }catch (Exception e){
                System.err.println("Błąd bazy: " + e);
            }
        }else if (odpowiedz.equalsIgnoreCase("aktualizuj")){
            try(Session session = HibernateUtil.INSTANCE.getSessionFactory().openSession()){
                Transaction transaction = session.beginTransaction();
                System.out.println("Podaj id pojazdu którego chcesz aktualizować:");
                String id = scanner.nextLine();
                Long pojazdId = Long.parseLong(id);

                Pojazd pojazd = session.get(Pojazd.class, pojazdId);

                if (pojazd == null) {
                    System.err.println("Student nie istnieje.");
                } else {
                    System.out.println("podaj marke");
                    String marka = scanner.nextLine();

                    System.out.println("podaj moc");
                    Double moc = Double.parseDouble(scanner.nextLine());

                    System.out.println("podaj kolor");
                    String kolor = scanner.nextLine();

                    Integer rokProdukcji = null;
                    do{
                        System.out.println("podaj rok produkcji");
                        rokProdukcji = Integer.parseInt(scanner.nextLine());
                    }while (!(rokProdukcji >= 1990 && rokProdukcji <= 2020));

                    System.out.println("czy jest elektryczny? (tak / nie)");
                    Boolean elektryczny = false;
                    String odpowiedzCzyElektryczny = scanner.nextLine();
                    if(odpowiedzCzyElektryczny.equalsIgnoreCase("tak")){
                        elektryczny = true;
                    } else if (odpowiedzCzyElektryczny.equalsIgnoreCase("nie")) {
                        elektryczny = false;
                    }else {
                        System.out.println("niepoprawna odpowiedzi i nie jest elektryczny");
                        elektryczny = false;
                    }

                    Pojazd pojazdAktualizowany = Pojazd.builder()
                            .marka(marka)
                            .moc(moc)
                            .kolor(kolor)
                            .rokProdukcji(rokProdukcji)
                            .elektryczny(elektryczny)
                            .id(pojazdId)
                            .build();

                    session.merge(pojazdAktualizowany);

                    transaction.commit();
                }
            }catch (Exception e){
                System.err.println("Błąd bazy: " + e);
            }
        } else {
            System.err.println("niepoprawna komenda");
        }


    }
}
