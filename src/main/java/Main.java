import org.hibernate.Session;
import org.hibernate.Transaction;

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
        }

    }
}
