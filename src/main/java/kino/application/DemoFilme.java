package kino.application;

import kino.application.data.Film;
import kino.application.data.FilmRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class DemoFilme implements CommandLineRunner {

    private final FilmRepository filmRepository;

    public DemoFilme(FilmRepository filmRepository) {
        this.filmRepository = filmRepository;
    }

    @Override
    public void run(String... args) {
        // läuft nur, wenn DB davor leer ist, also nochmal alles löschen und dann rerunnen. Sonst doppelt sich alles wegen der persistenz
        if (filmRepository.count() > 0) {
            return;
        }

        Film jumanji = new Film();
        jumanji.setTitel("JUMANJI");
        jumanji.setDauer(119);
        jumanji.setBeschreibung(
                "Vier Teenager entdecken beim Nachsitzen ein altes Videospiel, " +
                        "doch statt einem ungefährlichen Spaß vorm Fernseher wird das Quartett " +
                        "in die Dschungelwelt von Jumanji gezogen!"
        );
        jumanji.setFilmstart(LocalDate.of(2025, 11, 23));
        jumanji.setPosterUrl("/images/Jumanji.jpg");

        filmRepository.save(jumanji);

        Film fatalbert = new Film();
        fatalbert.setTitel("FAT ALBERT");
        fatalbert.setDauer(95);
        fatalbert.setBeschreibung(
                "FAT ALBERT und die Kids leben glücklich in ihrer Zeichentrick-Welt, doch als die junge Doris wegen fehlender Freunde Tränen vor ihrem Lieblingsprogramm vergießt, steigen die Animationsstars der 70er auf magische Art und Weise in die heutige Realität ein und bieten Ihre Hilfe an."
        );
        fatalbert.setFilmstart(LocalDate.of(2025, 12, 16));
        fatalbert.setPosterUrl("/images/fatalbert.jpg");

        filmRepository.save(fatalbert);

        Film nobody = new Film();
        nobody.setTitel("NOBODY");
        nobody.setDauer(120);
        nobody.setBeschreibung(
                "Hutch ist ein typischer Niemand, den keiner so richtig wahrnimmt. Wortlos erträgt der Ehemann und Vater die Demütigungen seines Alltags, ohne sich dagegen zu wehren. Doch als sein Töchterchen bei einem Diebstahl ihr heißgeliebtes Katzenarmband verliert, platzt ihm -- für alle überraschend -- der Kragen."
        );
        nobody.setFilmstart(LocalDate.of(2025, 12, 6));
        nobody.setPosterUrl("/images/nobody.jpg");

        filmRepository.save(nobody);

        Film minecraft = new Film();
        minecraft.setTitel("EIN MINECRAFT FILM");
        minecraft.setDauer(140);
        minecraft.setBeschreibung(
                "Vier Außenseiter werden durch ein geheimnisvolles Portal gezogen – mitten in ein skurriles kubisches Wunderland, das von der Vorstellungskraft lebt. Um wieder nach Hause zu kommen, begeben sie sich mit dem Handwerker Steve auf eine magische Reise."
        );
        minecraft.setFilmstart(LocalDate.of(2025, 12, 1));
        minecraft.setPosterUrl("/images/minecraft.jpg");

        filmRepository.save(minecraft);

        Film cars = new Film();
        cars.setTitel("CARS");
        cars.setDauer(100);
        cars.setBeschreibung(
                "Der erfolgreiche Rennwagen Lightning McQueen lebt sein Leben auf der Überholspur, bis er einen Umweg macht und in Radiator Springs strandet."
        );
        cars.setFilmstart(LocalDate.of(2025, 12, 10));
        cars.setPosterUrl("/images/cars.jpg");

        filmRepository.save(cars);

        Film larsLol = new Film();
        larsLol.setTitel("LARS IST LOL");
        larsLol.setDauer(95);
        larsLol.setBeschreibung(
                "Als Amanda nach den Sommerferien in die Schule zurückkehrt, soll sie sich als Mentorin um den neuen Mitschüler Lars kümmern, der das Down-Syndrom hat."
        );
        larsLol.setFilmstart(LocalDate.of(2025, 12, 5));
        larsLol.setPosterUrl("/images/larsLol.jpg");

        filmRepository.save(larsLol);

        Film larsFrauen = new Film();
        larsFrauen.setTitel("LARS UND DIE FRAUEN");
        larsFrauen.setDauer(95);
        larsFrauen.setBeschreibung(
                "Ein wahnhafter junger Mann beginnt eine unkonventionelle Beziehung mit einer Puppe, die er im Internet findet."
        );
        larsFrauen.setFilmstart(LocalDate.of(2025, 12, 9));
        larsFrauen.setPosterUrl("/images/larsFrauen.jpg");

        filmRepository.save(larsFrauen);

        Film max = new Film();
        max.setTitel("MAX & CO");
        max.setDauer(95);
        max.setBeschreibung(
                "Mit 15 Jahren reist Max nach Saint-Hilare, wo er seinen Vater zu finden hofft, den berühmten Johnny Bigoude, der kurze Zeit vor seiner Geburt verschwand."
        );
        max.setFilmstart(LocalDate.of(2025, 12, 7));
        max.setPosterUrl("/images/max.jpg");

        filmRepository.save(max);

        Film hannah = new Film();
        hannah.setTitel("HANNAH MONTANA");
        hannah.setDauer(95);
        hannah.setBeschreibung(
                "Auf Drängen ihres Vaters reist Miley Stewart auf Drängen ihres Vaters in ihre Heimatstadt Crowley Corners, Tennessee, um sich einen Überblick darüber zu verschaffen, was im Leben am wichtigsten ist."
        );
        hannah.setFilmstart(LocalDate.of(2025, 12, 23));
        hannah.setPosterUrl("/images/hannah.jpg");

        filmRepository.save(hannah);

        Film jonas = new Film();
        jonas.setTitel("JONAS");
        jonas.setDauer(95);
        jonas.setBeschreibung(
                "Auf Drängen ihres Vaters reist Miley Stewart auf Drängen ihres Vaters in ihre Heimatstadt Crowley Corners, Tennessee, um sich einen Überblick darüber zu verschaffen, was im Leben am wichtigsten ist."
        );
        jonas.setFilmstart(LocalDate.of(2025, 12, 19));
        jonas.setPosterUrl("/images/jonas.jpg");

        filmRepository.save(jonas);

        Film leon = new Film();
        leon.setTitel("LEON DER PROFI");
        leon.setDauer(95);
        leon.setBeschreibung(
                "Nachdem ihre Familie ermordet wurde, wird Mathilda, ein 12-jähriges Mädchen, von Leon, einem professionellen Killer, widerwillig bei sich aufgenommen."
        );
        leon.setFilmstart(LocalDate.of(2025, 12, 12));
        leon.setPosterUrl("/images/leon.jpg");

        filmRepository.save(leon);

        Film jason = new Film();
        jason.setTitel("SEIN NAME WAR JASON");
        jason.setDauer(95);
        jason.setBeschreibung(
                "Eine Gruppe jugendlicher Aufseher wird von einem unbekannten Täter verfolgt und ermordet, während sie versuchen, ein Ferienlager wieder zu eröffnen, das Jahre zuvor nach dem Ertrinkungstod eines Kindes geschlossen wurde."
        );
        jason.setFilmstart(LocalDate.of(2025, 12, 13));
        jason.setPosterUrl("/images/jason.jpeg");

        filmRepository.save(jason);




    }
}
