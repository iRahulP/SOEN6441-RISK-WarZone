package model;


public class RunMap {

	public void showMap(GameMap map) {
		if(map==null)
			return;
		System.out.printf("%25s%25s%35s\n", "Continents", "Country", "Country's neighbors");
		System.out.printf("%85s\n", "-------------------------------------------------------------------------------------------");
		boolean l_PrintContinentName = true;
		boolean l_PrintCountryName = true;
		for(Continent continent : map.getContinents().values()) {
			if(continent.getCountries().size()==0) {
				System.out.printf("\n%25s%25s%25s\n", continent.getContinentName(), "", "");
			}
			for(CountryDetails country : continent.getCountries().values()) {
				if(country.getNeighbours().size()==0) {
					if(l_PrintContinentName && l_PrintCountryName) {
						System.out.printf("\n%25s%25s%25s\n", continent.getContinentName(), country.getCountryName(), "");
						l_PrintContinentName = false;
						l_PrintCountryName = false;
					}
					else if(l_PrintCountryName) {
						System.out.printf("\n%25s%25s%25s\n", "", country.getCountryName(), "");
						l_PrintCountryName =  false;
					}
				}
				for(CountryDetails neighbor : country.getNeighbours().values()) {
					if(l_PrintContinentName && l_PrintCountryName) {
						System.out.printf("\n%25s%25s%25s\n", continent.getContinentName(), country.getCountryName(), neighbor.getCountryName());
						l_PrintContinentName = false;
						l_PrintCountryName = false;
					}
					else if(l_PrintCountryName) {
						System.out.printf("\n%25s%25s%25s\n", "", country.getCountryName(), neighbor.getCountryName());
						l_PrintCountryName = false;
					}
					else {
						System.out.printf("%25s%25s%25s\n", "", "", neighbor.getCountryName());
					}
				}
				l_PrintCountryName = true;
			}
			l_PrintContinentName = true;
			l_PrintCountryName = true;
		}
	}
	
}
