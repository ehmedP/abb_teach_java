�� Tapşırıq: &quot;Əməkdaşların Maaş Analiz Sistemi&quot; (Stream + Lambda əsaslı)
Şərt:
data/employees.txt faylında hər sətirdə bu formatda məlumat var:
ad,şöbə,maaş,yaş
Məsələn:
Elvin,IT,1800,25
Aysel,Marketing,1200,30
Rəşad,IT,2500,35
Nərmin,HR,1100,28
Tural,IT,3000,40
Günel,Marketing,1500,26
Proqram bu əməliyyatları yalnız Stream API və Lambda istifadə edərək yerinə yetirsin:
1. Faylı oxuyub List&lt;Employee&gt; yaratsın (Files.lines() ilə stream vasitəsilə).
2. Şöbələrə görə qruplaşdırsın (Collectors.groupingBy) → Map&lt;String,
   List&lt;Employee&gt;&gt;
3. Hər şöbənin orta maaşını hesablasın (Collectors.averagingDouble)
4. Maaşı 2000-dən çox olan əməkdaşları tapsın (filter)
5. Əməkdaşları maaşa görə azalan sırayla düzsün (sorted + Comparator)
6. Ən yüksək maaş alan əməkdaşı tapsın (max)
7. Bütün adları vergüllə ayrılmış tək sətirdə birləşdirsin (Collectors.joining)
8. Nəticələri data/salary_report.txt faylına yazsın (Files.write və ya
   BufferedWriter)
9. Bonus: Şöbə üzrə əməkdaş sayını tapsın (Collectors.counting)