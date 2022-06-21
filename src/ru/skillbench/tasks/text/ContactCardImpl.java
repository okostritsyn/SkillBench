package ru.skillbench.tasks.text;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class ContactCardImpl implements ContactCard {

    private String fullName;
    private String organization;
    private char gender;
    private String dayOfBirth;
    private HashMap<String, String>  telephone;

    /**
     * Основной метод парсинга: создает экземпляр карточки из источника данных (Scanner),
     * содержащего следующие текстовые данные о человеке (5 полей):<br/>
     * 1) FN - Полное имя - обязательное поле<br/>
     * 2) ORG - Организация - обязательное поле<br/>
     * 3) GENDER - Пол - один символ: F или M. Это поле в данных может отсутствовать.<br/>
     * 4) BDAY - Дата рождения - в следующем формате: "DD-MM-YYYY", где DD - 2 цифры, обозначающие день,
     * MM - 2 цифры, обозначающие месяц, YYYY - 4 цифры, обозначающие год.
     * Это поле в данных может отсутствовать.<br/>
     * 5) TEL - Номер телефона - ровно 10 цифр, не включающие код страны. Полей TEL может быть 0 или несколько,
     * и разные поля TEL различаются значением обязательного атрибута TYPE, например:
     * TEL;TYPE=HOME,VOICE:4991112233<br/>
     * <p/>
     * Каждое из этих полей в источнике данных расположено на отдельной строке;
     * строки в стандарте vCard отделяются символом CRLF (\r\n).<br/>
     * Имя поля отделяется от его значения двоеточием, например: GENDER:F<br/>
     * Если нужно, можно предположить, что порядок полей будет именно такой, как выше.<br/>
     * Но первой строкой всегда идет BEGIN:VCARD, последней - END:VCARD.<br/>
     * Пример содержимого Scanner:<br/>
     * <code>
     * BEGIN:VCARD
     * FN:Forrest Gump
     * ORG:Bubba Gump Shrimp Co.
     * BDAY:06-06-1944
     * TEL;TYPE=WORK,VOICE:4951234567
     * TEL;TYPE=CELL,VOICE:9150123456
     * END:VCARD
     * </code>
     * <p/>
     * ПРИМЕЧАНИЕ: Такой метод в реальных приложениях был бы static, однако
     * система проверки учебных задач проверяет только не-статические методы.
     *
     * @param scanner Источник данных
     * @return {@link ContactCard}, созданный из этих данных
     * @throws InputMismatchException Возникает, если структура или значения данных не соответствуют формату,
     *                                описанному выше; например, если после названия поля нет двоеточия или дата указана в ином формате
     *                                или номер телефона содержит неверное число цифр.
     * @throws NoSuchElementException Возникает, если данные не содержат обязательных полей
     *                                (FN, ORG, BEGIN:VCARD, END:VCARD)
     */
    @Override
    public ContactCard getInstance(Scanner scanner) throws InputMismatchException,NoSuchElementException {
        if (!scanner.hasNext("BEGIN:VCARD")){
            throw new NoSuchElementException("Cannot find begin section");
        }
        telephone = new HashMap<>();
        scanner.useDelimiter("\r\n");
        boolean hasEndCard = false;
        while (scanner.hasNext()){
            String currString = scanner.next();
            if (currString.contains("FN:")) {this.fullName = currString.replaceFirst("FN:","").trim();}
            if (currString.contains("ORG:")) {this.organization = currString.replaceFirst("ORG:","").trim();}
            if (currString.contains("GENDER:")) {this.gender = currString.replaceFirst("GENDER:","").charAt(0);}
            if (currString.contains("BDAY:")) {
                String currDate = currString.replaceFirst("BDAY:","").trim();
                if (currDate.matches("^\\d{2}-\\d{2}-\\d{4}")){
                   this.dayOfBirth = currDate;
                } else {
                    System.out.println("BDAY = " + currDate);
                    throw new InputMismatchException("Incorrect date of birth");
                }
            }
            if (currString.contains("TEL;")) {
                String currPhone = currString.replaceFirst("TEL;TYPE=","").trim();
                StringBuilder digits= new StringBuilder();
                StringBuilder type= new StringBuilder();
                boolean containsColon = false;
                for (int i = 0; i < currPhone.length(); i++) {
                    char chrs = currPhone.charAt(i);
                    if (chrs == ':') {
                        containsColon = true;
                    }
                    else if (Character.isDigit(chrs)){
                        digits.append(chrs);
                    } else {
                        type.append(chrs);
                    }
                }
                if (type.toString().trim().isEmpty() || !containsColon){
                    throw new InputMismatchException("Incorrect type of telephone");
                }
                if (digits.toString().trim().length() != 10){
                    throw new InputMismatchException("Incorrect number of telephone");
                }
                this.telephone.put(type.toString().trim(),digits.toString().trim());
            }
            if (currString.contains("END:VCARD")) {hasEndCard = true; break;}
        }
        if (!hasEndCard){
            throw new NoSuchElementException("Cannot find end section");
        }
        if (this.fullName == null || this.fullName.isEmpty()){
            throw new InputMismatchException("Incorrect full name");
        }
        if (this.organization == null || this.organization.isEmpty()){
            throw new InputMismatchException("Incorrect organization");
        }
        if (this.gender != '\u0000' && this.gender != 'F' && this.gender != 'M' ){
            throw new InputMismatchException("Incorrect gender");
        }
        return this;
    }

    /**
     * Метод создает {@link Scanner} и вызывает {@link #getInstance(Scanner)}
     *
     * @param data Данные для разбора, имеющие формат, описанный в {@link #getInstance(Scanner)}
     * @return {@link ContactCard}, созданный из этих данных
     */
    @Override
    public ContactCard getInstance(String data) {
        Scanner scanner = new Scanner(data);
        return getInstance(scanner);
     }

    /**
     * @return Полное имя - значение vCard-поля FN: например, "Forrest Gump"
     */
    @Override
    public String getFullName() {
        return this.fullName;
    }

    /**
     * @return Организация - значение vCard-поля ORG: например, "Bubba Gump Shrimp Co."
     */
    @Override
    public String getOrganization() {
        return this.organization;
    }

    /**
     * Если поле GENDER отсутствует в данных или равно "M", этот метод возвращает false
     *
     * @return true если этот человек женского пола (GENDER:F)
     */
    @Override
    public boolean isWoman() {
        return this.gender == 'F';
    }

    /**
     * ПРИМЕЧАНИЕ: в современных приложениях рекомендуется для работы с датой применять java.time.LocalDate,
     * однако такие классы как java.util.Calendar или java.util.Date необходимо знать.
     *
     * @return День рождения человека в виде {@link Calendar}
     * @throws NoSuchElementException Если поле BDAY отсутствует в данных
     */
    @Override
    public Calendar getBirthday() {
        if (this.dayOfBirth == null || this.dayOfBirth.isEmpty()) throw new NoSuchElementException("birth day is not set!");
        Calendar calendar = Calendar.getInstance();
        calendar.clear();
        String [] massOfDates = dayOfBirth.split("-");
        int day  = 0 ,month  = 0,year = 0;
        int i = 1;
        for (String currPart:
                massOfDates) {
            if(i==1) day = Integer.parseInt(currPart.trim());
            if(i==2) month = Integer.parseInt(currPart.trim());
            if(i==3) year = Integer.parseInt(currPart.trim());
            i++;
        }
        calendar.set(year,month-1,day,0,0,0);
        calendar.setTimeInMillis(calendar.getTimeInMillis()); // to set all fields in calendar object (setTime calls computeFields())
        return calendar;
    }

    /**
     * ПРИМЕЧАНИЕ: В реализации этого метода рекомендуется использовать {@link DateTimeFormatter}
     *
     * @return Возраст человека на данный момент в виде {@link Period}
     * @throws NoSuchElementException Если поле BDAY отсутствует в данных
     */
    @Override
    public Period getAge() {
        Calendar birthDay = getBirthday();
        LocalDate currDay = LocalDateTime.ofInstant(birthDay.toInstant(), birthDay.getTimeZone().toZoneId()).toLocalDate();
        return Period.between(currDay, LocalDate.now());
    }

    /**
     * @return Возраст человека в годах: например, 74
     * @throws NoSuchElementException Если поле BDAY отсутствует в данных
     */
    @Override
    public int getAgeYears() {
        Period currPeriod = getAge();
        return currPeriod.getYears();
    }

    /**
     * Возвращает номер телефона в зависимости от типа.
     *
     * @param type Тип телефона, который содержится в данных между строкой "TEL;TYPE=" и двоеточием
     * @return Номер телефона - значение vCard-поля TEL, приведенное к следующему виду: "(123) 456-7890"
     * @throws NoSuchElementException если в данных нет телефона указанного типа
     */
    @Override
    public String getPhone(String type) {

        if (this.telephone.containsKey(type)) {
            String currPhone = this.telephone.get(type);
            return "("+currPhone.substring(0,3)+") "+currPhone.substring(3,6)+"-"+currPhone.substring(6,10);
        } else throw new NoSuchElementException("No such type of telephone");
     }
}
