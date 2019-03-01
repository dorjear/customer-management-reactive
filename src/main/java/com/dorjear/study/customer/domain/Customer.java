package com.dorjear.study.customer.domain;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Version;

import com.fasterxml.jackson.annotation.JsonFormat;

import io.swagger.annotations.ApiModelProperty;
/**
 * 
 * @author dorjear
 * @Note This JPA model is not a best design. The relation between Customer and Address can be with a better design. Only for short term solution
 * Forget about the DB, this design is good for the new down stream CRMS with Restful call.
 */
@Entity
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @ApiModelProperty(notes = "The database generated customer ID")
    private Integer id;
    @Version
    @ApiModelProperty(notes = "The auto-generated version of the customer")
    private Integer version;
    @ApiModelProperty(notes = "The application-specific customer ID")
    private String customerId;
    @ApiModelProperty(notes = "The first name")
    private String firstName;
    @ApiModelProperty(notes = "The last name")
    private String lastName;
    @ApiModelProperty(notes = "The date of birth")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    private Date dateOfBirth;
    @ApiModelProperty(notes = "The home address")
    @OneToOne(cascade = CascadeType.ALL)
    private Address homeAddress;
    @OneToOne(cascade = CascadeType.ALL)
    @ApiModelProperty(notes = "The postal address")
    private Address postalAddress;
    @OneToOne(cascade = CascadeType.ALL)
    @ApiModelProperty(notes = "The work address")
    private Address workAddress;
    
    
    
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getVersion() {
		return version;
	}
	public void setVersion(Integer version) {
		this.version = version;
	}
	public String getCustomerId() {
		return customerId;
	}
	public void setCustomerId(String customerId) {
		this.customerId = customerId;
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
	public Date getDateOfBirth() {
		return dateOfBirth;
	}
	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}
    public Address getHomeAddress() {
        return homeAddress;
    }
    public void setHomeAddress(Address homeAddress) {
        this.homeAddress = homeAddress;
    }
    public Address getPostalAddress() {
        return postalAddress;
    }
    public void setPostalAddress(Address postalAddress) {
        this.postalAddress = postalAddress;
    }
    public Address getWorkAddress() {
        return workAddress;
    }
    public void setWorkAddress(Address workAddress) {
        this.workAddress = workAddress;
    }

}
