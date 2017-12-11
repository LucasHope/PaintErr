package fi.academy;

public class Country {

    String Code, Name, Continent, Region, LocalName, GovernmentForm, HeadOfState, Code2;
    double SurfaceArea, LifeExpectancy, GNP, GNPOId;
    int IndepYear, Population, Capital;

    // code, name, continent, region, headofstate, population

    public Country(String code, String name, String continent, String region, String headOfState, int population) {
        Code = code;
        Name = name;
        Continent = continent;
        Region = region;
        HeadOfState = headOfState;
        Population = population;
    }

    public String print() {
        return "" + this.Code + " - " + this.Name + " - led by " + this.HeadOfState + " with a population of " + this.Population;
    }

    @Override
    public String toString() {
        return this.print();
    }

    public Country(String code, String name, String continent, String region, String localName, String governmentForm, String headOfState, String code2, double surfaceArea, double lifeExpectancy, double GNP, double GNPOId, int indepYear, int population, int capital) {
        Code = code;
        Name = name;
        Continent = continent;
        Region = region;
        LocalName = localName;
        GovernmentForm = governmentForm;
        HeadOfState = headOfState;
        Code2 = code2;
        SurfaceArea = surfaceArea;
        LifeExpectancy = lifeExpectancy;
        this.GNP = GNP;
        this.GNPOId = GNPOId;
        IndepYear = indepYear;
        Population = population;
        Capital = capital;
    }

    public String getCode() {

        return Code;
    }

    public void setCode(String code) {
        Code = code;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getContinent() {
        return Continent;
    }

    public void setContinent(String continent) {
        Continent = continent;
    }

    public String getRegion() {
        return Region;
    }

    public void setRegion(String region) {
        Region = region;
    }

    public String getLocalName() {
        return LocalName;
    }

    public void setLocalName(String localName) {
        LocalName = localName;
    }

    public String getGovernmentForm() {
        return GovernmentForm;
    }

    public void setGovernmentForm(String governmentForm) {
        GovernmentForm = governmentForm;
    }

    public String getHeadOfState() {
        return HeadOfState;
    }

    public void setHeadOfState(String headOfState) {
        HeadOfState = headOfState;
    }

    public String getCode2() {
        return Code2;
    }

    public void setCode2(String code2) {
        Code2 = code2;
    }

    public double getSurfaceArea() {
        return SurfaceArea;
    }

    public void setSurfaceArea(double surfaceArea) {
        SurfaceArea = surfaceArea;
    }

    public double getLifeExpectancy() {
        return LifeExpectancy;
    }

    public void setLifeExpectancy(double lifeExpectancy) {
        LifeExpectancy = lifeExpectancy;
    }

    public double getGNP() {
        return GNP;
    }

    public void setGNP(double GNP) {
        this.GNP = GNP;
    }

    public double getGNPOId() {
        return GNPOId;
    }

    public void setGNPOId(double GNPOId) {
        this.GNPOId = GNPOId;
    }

    public int getIndepYear() {
        return IndepYear;
    }

    public void setIndepYear(int indepYear) {
        IndepYear = indepYear;
    }

    public int getPopulation() {
        return Population;
    }

    public void setPopulation(int population) {
        Population = population;
    }

    public int getCapital() {
        return Capital;
    }

    public void setCapital(int capital) {
        Capital = capital;
    }
}
