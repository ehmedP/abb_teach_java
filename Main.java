import src.service.CompanyAnalyticsManager;

void main() {

    /**
     *
     * IMPORTANT
     *
     * Tasklarin coxu sadece verilmis qaydaya esasen icra etmedir. Mueyyen bir problemi hell etmir.
     * Meselen maksimum maasi olan employee tapmaq bir problem deyil, ona gore elave conditionlar vs nezere ala bilmirem.
     * Meselen maasi eyni olan 5 employee olanda hansini goturum ? bu filter tam olaraq ne ucun istenilir vs .
     * Buna gore sadece taskda istenilene fokus oldum.
     *
     */

    CompanyAnalyticsManager manager = new CompanyAnalyticsManager();

    manager.execute();

    /**
     *
     *
     * Eyni hesabatı 500,000 elementlik süni generasiya olunmuş List<Employee> üzərində işlət:
     * • Bir dəfə stream() ilə, bir dəfə parallelStream() ilə çalışdır, hər ikisinin vaxtını ölç
     * • Hansı bölmələrdə paralel streamin fayda verdiyini qeyd et
     * • Hansı bölmələrdə (məsələn kiçik map-lərdə) overhead yaratdığını qeyd et
     *
     *
     * =================================================================================================================
     *
     * 500,000 Employee üzərində stream() və parallelStream() müqayisə.
     *
     *
     */

}
