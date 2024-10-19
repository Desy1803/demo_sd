package com.example.demo_sd.dto;

import com.example.demo_sd.entities.UserEntity;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.LocalDate;

    public class UserRequest {

        public String getUsername() {
            return username;
        }

        public String getPassword() {
            return password;
        }

        public String getFirstName() {
            return firstName;
        }

        public String getLastName() {
            return lastName;
        }

        public String getEmail() {
            return email;
        }

        public LocalDate getBirthday() {
            return birthday;
        }

        public String getPhoneNumber() {
            return phoneNumber;
        }

        public String getAddress() {
            return address;
        }

        public String getCity() {
            return city;
        }

        public String getCountry() {
            return country;
        }

        @JsonProperty("username")
        private String username;

        @JsonProperty("password")
        private String password;

        @JsonProperty("first_name")
        private String firstName;

        @JsonProperty("last_name")
        private String lastName;

        public void setUsername(String username) {
            this.username = username;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public void setFirstName(String firstName) {
            this.firstName = firstName;
        }

        public void setLastName(String lastName) {
            this.lastName = lastName;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public void setBirthday(LocalDate birthday) {
            this.birthday = birthday;
        }

        public void setPhoneNumber(String phoneNumber) {
            this.phoneNumber = phoneNumber;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public void setCountry(String country) {
            this.country = country;
        }

        @JsonProperty("email")
        private String email;

        @JsonProperty("birthday")
        private LocalDate birthday;

        @JsonProperty("phone_number")
        private String phoneNumber;

        @JsonProperty("address")
        private String address;

        @JsonProperty("city")
        private String city;

        @JsonProperty("country")
        private String country;



        @Override
        public String toString() {
            return "UserRequest{" +
                    "username='" + username + '\'' +
                    ", password='" + password + '\'' +
                    ", firstName='" + firstName + '\'' +
                    ", lastName='" + lastName + '\'' +
                    ", email='" + email + '\'' +
                    ", birthday=" + birthday +
                    ", phoneNumber='" + phoneNumber + '\'' +
                    ", address='" + address + '\'' +
                    ", city='" + city + '\'' +
                    ", country='" + country + '\'' +
                    '}';
        }




    public static UserEntity toModel(UserRequest user) {
        return new UserEntity(user.firstName,user.lastName, user.email, user.birthday, user.phoneNumber, user.address, user.city, user.country);
    }
}

