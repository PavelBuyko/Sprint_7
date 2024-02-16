package resources;

import java.util.List;

public class JSONBuilder {
    // поля для создания/авторизации курьера
    private String login;
    private String track;
    private String password;
    private String firstName;
    // поля для создания заказа
    private String lastName;
    private String address;
    private String metroStation;
    private String phone;
    private String rentTime;
    private String deliveryDate;
    private String comment;
    private List<String> color;
    //Получение списка заказов
    private String courierId;
    private String nearestStation;
    private String limit;
    private String page;
    // поля для удаления курьера
    private String id;

    //конструктор для логина
    public JSONBuilder(String login, String password) {
        this.login = login;
        this.password = password;
    }
    //конструктор для создания курьера
    public JSONBuilder(String login, String password, String firstName)
    {
        this.login = login;
        this.password = password;
        this.firstName = firstName;
    }
    public JSONBuilder(){}

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }
    public void setLogin(String login)
    {
        this.login = login;
    }
    public void setRandomLogin()
    {
        this.login = RandomString.generateRandomString(30);
    }

    public String getPassword() {return password;}
    public void setPassword(String password ) {
        this.password = password;
    }
    public void setRandomPassword() {
        this.password = RandomString.generateRandomString(30);
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getMetroStation() {
        return metroStation;
    }

    public void setMetroStation(String metroStation) {
        this.metroStation = metroStation;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getRentTime() {
        return rentTime;
    }

    public void setRentTime(String rentTime) {
        this.rentTime = rentTime;
    }

    public String getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(String deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getTrack() {
        return track;
    }

    public void setTrack(String track) {
        this.track = track;
    }

    public List<String> getColor() {
        return color;
    }

    public void setColor(List<String> color) {
        this.color = color;
    }

    public String getCourierId() {
        return courierId;
    }

    public void setCourierId(String courierId) {
        this.courierId = courierId;
    }

    public String getNearestStation() {
        return nearestStation;
    }

    public void setNearestStation(String nearestStation) {
        this.nearestStation = nearestStation;
    }

    public String getLimit() {
        return limit;
    }

    public void setLimit(String limit) {
        this.limit = limit;
    }

    public String getPage() {
        return page;
    }

    public void setPage(String page) {
        this.page = page;
    }




}
