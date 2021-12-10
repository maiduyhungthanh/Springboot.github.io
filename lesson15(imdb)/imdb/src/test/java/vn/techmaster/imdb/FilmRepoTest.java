package vn.techmaster.imdb;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import vn.techmaster.imdb.model.Film;
import vn.techmaster.imdb.repository.FilmRepository;

@SpringBootTest
class FilmRepoTest {
	@Autowired private FilmRepository filmRepo;

	@Test
	public void getAll() {
		List<Film> filmList = filmRepo.getAll();
		int sizeList = filmRepo.getAll().get(filmRepo.getAll().size()-1).getId();
		assertEquals(filmList.size(), sizeList);
	}

	@Test
	public void getFilmByCountry() {
		Map<String, List<Film>> filmListByCountry = filmRepo.getFilmByCountry();
		int sizeBrazil = 3;
		assertEquals(filmListByCountry.get("Brazil").size(), sizeBrazil);
	}

	@Test
	public void getcountryMakeMostFilms() {
		Entry<String, Integer> counter = filmRepo.getcountryMakeMostFilms();
		String mostCount = "China";
		int mostAmount = 9;
		assertEquals(counter.getKey(), mostCount);
		assertEquals(counter.getValue(), mostAmount);
	}

	@Test
	public void yearMakeMostFilms() {
		Entry<Integer, Integer> counter = filmRepo.yearMakeMostFilms();
		assertEquals(counter.getKey(), 1985);
		assertEquals(counter.getValue(), 4);
	}

	@Test
	public void getAllGeneres() {
		List<String> generes = filmRepo.getAllGeneres();
		String love = "love";
		assertTrue(generes.contains(love));

	}

	@Test
	public void getFilmsMadeByCountryFromYearToYear() {
		List<Film> filmList = filmRepo.getFilmsMadeByCountryFromYearToYear("Brazil", 1985, 1987);
		String nBrazill = "All Dogs Go to Heaven 2";
		assertEquals(filmList.get(1).getTitle(),nBrazill );

	}

	@Test
	public void categorizeFilmByGenere() {
		Map<String, List<Film>> filmList = filmRepo.categorizeFilmByGenere();
		assertEquals(filmList.keySet().size(), 15);
		assertEquals(filmList.get("sex").size(), 8);
		assertEquals(filmList.get("love").size(), 5);
	}

	@Test
	public void top5HighMarginFilms() {
		List<Film> filmList = filmRepo.top5HighMarginFilms();
		int sizeTop5 = 5;
		String nameFilm5 = "Spring in a Small Town (Xiao cheng zhi chun)";
		assertEquals(filmList.size(), sizeTop5);
		assertEquals(filmList.get(4).getTitle(), nameFilm5);
	}

	@Test
	public void top5HighMarginFilmsIn1990to2000() {
		List<Film> filmList = filmRepo.top5HighMarginFilmsIn1990to2000();
		assertEquals(filmList.size(), 5);
		String nameFilm5 = "Coluche, l'histoire d'un mec";
		assertEquals(filmList.get(4).getTitle(), nameFilm5);


	}

	@Test
	public void ratioBetweenGenere() {
		double filmRatio = filmRepo.ratioBetweenGenere("sex", "love");
		double sex = 8;
		double love = 5;
		assertEquals(filmRatio, sex/love);
		
	}

	@Test
	public void top5FilmsHighRatingButLowMargin() {
		List<Film> filmList = filmRepo.top5FilmsHighRatingButLowMargin();
		assertEquals(filmList.size(), 5);
	}
}
