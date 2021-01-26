
public class YhteenvetokyselytOsa2 {

    public static void main(String[] args) {
        System.out.println(kysely1());
        System.out.println(kysely2());
        System.out.println(kysely3());

    }

    public static String kysely1() {
        return "SELECT Genre.Name AS genre, COUNT(Track.TrackId) AS kappaleita FROM Track, Genre  \n"
                + " WHERE Genre.GenreId = Track.GenreId\n"
                + " GROUP BY Genre.Name;";
    }

    public static String kysely2() {
        return "SELECT Genre.Name AS genre, COUNT(Invoice.InvoiceId) AS ostettuja FROM Genre, Track, InvoiceLine, Invoice\n"
                + " WHERE Genre.GenreId = Track.GenreId\n"
                + " AND Track.TrackId = InvoiceLine.TrackId\n"
                + " AND InvoiceLine.InvoiceId = Invoice.InvoiceId\n"
                + " GROUP BY Genre.Name;";
    }

    public static String kysely3() {
        return "SELECT Artist.Name AS artisti, COUNT(Album.AlbumId) AS levyt\n"
                + " FROM Artist\n"
                + " LEFT JOIN Album ON Artist.ArtistId = Album.ArtistId\n"
                + " GROUP BY Artist.Name;";
    }

}
