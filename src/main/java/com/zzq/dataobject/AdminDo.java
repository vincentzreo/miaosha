package com.zzq.dataobject;

public class AdminDo {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column admin_user.id
     *
     * @mbg.generated Sat Apr 27 11:16:39 CST 2019
     */
    private Integer id;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column admin_user.full_name
     *
     * @mbg.generated Sat Apr 27 11:16:39 CST 2019
     */
    private String fullName;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column admin_user.email
     *
     * @mbg.generated Sat Apr 27 11:16:39 CST 2019
     */
    private String email;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column admin_user.address
     *
     * @mbg.generated Sat Apr 27 11:16:39 CST 2019
     */
    private String address;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column admin_user.user_name
     *
     * @mbg.generated Sat Apr 27 11:16:39 CST 2019
     */
    private String userName;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column admin_user.password
     *
     * @mbg.generated Sat Apr 27 11:16:39 CST 2019
     */
    private String password;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column admin_user.id
     *
     * @return the value of admin_user.id
     *
     * @mbg.generated Sat Apr 27 11:16:39 CST 2019
     */
    public Integer getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column admin_user.id
     *
     * @param id the value for admin_user.id
     *
     * @mbg.generated Sat Apr 27 11:16:39 CST 2019
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column admin_user.full_name
     *
     * @return the value of admin_user.full_name
     *
     * @mbg.generated Sat Apr 27 11:16:39 CST 2019
     */
    public String getFullName() {
        return fullName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column admin_user.full_name
     *
     * @param fullName the value for admin_user.full_name
     *
     * @mbg.generated Sat Apr 27 11:16:39 CST 2019
     */
    public void setFullName(String fullName) {
        this.fullName = fullName == null ? null : fullName.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column admin_user.email
     *
     * @return the value of admin_user.email
     *
     * @mbg.generated Sat Apr 27 11:16:39 CST 2019
     */
    public String getEmail() {
        return email;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column admin_user.email
     *
     * @param email the value for admin_user.email
     *
     * @mbg.generated Sat Apr 27 11:16:39 CST 2019
     */
    public void setEmail(String email) {
        this.email = email == null ? null : email.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column admin_user.address
     *
     * @return the value of admin_user.address
     *
     * @mbg.generated Sat Apr 27 11:16:39 CST 2019
     */
    public String getAddress() {
        return address;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column admin_user.address
     *
     * @param address the value for admin_user.address
     *
     * @mbg.generated Sat Apr 27 11:16:39 CST 2019
     */
    public void setAddress(String address) {
        this.address = address == null ? null : address.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column admin_user.user_name
     *
     * @return the value of admin_user.user_name
     *
     * @mbg.generated Sat Apr 27 11:16:39 CST 2019
     */
    public String getUserName() {
        return userName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column admin_user.user_name
     *
     * @param userName the value for admin_user.user_name
     *
     * @mbg.generated Sat Apr 27 11:16:39 CST 2019
     */
    public void setUserName(String userName) {
        this.userName = userName == null ? null : userName.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column admin_user.password
     *
     * @return the value of admin_user.password
     *
     * @mbg.generated Sat Apr 27 11:16:39 CST 2019
     */
    public String getPassword() {
        return password;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column admin_user.password
     *
     * @param password the value for admin_user.password
     *
     * @mbg.generated Sat Apr 27 11:16:39 CST 2019
     */
    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }
}