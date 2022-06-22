package ru.skillbench.tasks.basics.entity;

public class EmployeeImpl implements Employee
{
    private int salary;
    private String firstName;
    private String lastName;
    private Employee manager;

    public EmployeeImpl() {
        this.salary = 1000;
    }

    /**
     * @return Зарплата сотрудника на настоящий момент.
     */
    @Override
    public int getSalary() {
        return this.salary;
    }

    /**
     * Увеличивает зарплату сотрудника на заданное значение
     *
     * @param value Значение, на которое нужно увеличить
     */
    @Override
    public void increaseSalary(int value) {
        this.salary += value;
    }

    /**
     * @return Имя сотрудника
     */
    @Override
    public String getFirstName() {
        return this.firstName;
    }

    /**
     * Устанавливает имя сотрудника
     *
     * @param firstName Новое имя
     */
    @Override
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * @return Фамилия сотрудника
     */
    @Override
    public String getLastName() {
        return this.lastName;
    }

    /**
     * Устанавливает фамилию сотрудника
     *
     * @param lastName Новая фамилия
     */
    @Override
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * @return Имя и затем фамилия сотрудника, разделенные символом " " (пробел)
     */
    @Override
    public String getFullName() {
        return firstName+" "+lastName;
    }

    /**
     * Устанавливает Менеджера сотрудника.
     *
     * @param manager Сотрудник, являющийся менеджером данного сотрудника.
     *                НЕ следует предполагать, что менеджер является экземпляром класса EmployeeImpl.
     */
    @Override
    public void setManager(Employee manager) {
        this.manager = manager;
    }

    /**
     * @return Имя и фамилия Менеджера, разделенные символом " " (пробел).
     * Если Менеджер не задан, возвращает строку "No manager".
     */
    @Override
    public String getManagerName() {
        if (this.manager == null) {
            return "No manager";
        }
        return this.manager.getFullName();
    }

    /**
     * Возвращает Менеджера верхнего уровня, т.е. вершину иерархии сотрудников,
     * в которую входит данный сотрудник.
     * Если над данным сотрудником нет ни одного менеджера, возвращает данного сотрудника.
     * Замечание: поскольку менеджер, установленный методом {@link #setManager(Employee)},
     * может быть экзепляром другого класса, при поиске топ-менеджера нельзя обращаться
     * к полям класса EmployeeImpl. Более того, поскольку в интерфейсе Employee не объявлено
     * метода getManager(), поиск топ-менеджера невозможно организовать в виде цикла.
     * Вместо этого нужно использовать рекурсию (и это "более объектно-ориентированно").
     */
    @Override
    public Employee getTopManager() {
        if (this.manager == null) {
            return this;
        }
        Employee currManager = this.manager;
        while (true) {
            Employee newManager = currManager.getTopManager();
            if (newManager.equals(currManager)) break;
            currManager = newManager;
        }
        return currManager;
    }
}