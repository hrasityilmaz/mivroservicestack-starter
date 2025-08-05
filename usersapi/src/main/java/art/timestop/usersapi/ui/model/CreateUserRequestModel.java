package art.timestop.usersapi.ui.model;



import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class CreateUserRequestModel {

    @NotNull(message="First Name cant be null")
    @Size(min=2, message="First name cant be less than 2 characters!")
    private String firstName;
    
    @NotNull(message="Last Name cant be null")
    @Size(min=2, message="Last name cant be less than 2 characters!")
    private String lastName;

     @NotNull(message="Password cant be null")
    @Size(min=6, max=16, message="Pasword at least 6 max 16 characters!")
    private String password;

    @NotNull(message="Email cant be null")
    @Email
    private String email;

    private String isAdmin = "0";
    private String isCommerical ="0";
    
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
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getIsAdmin() {
        return isAdmin;
    }
    public void setIsAdmin(String isAdmin) {
        this.isAdmin = isAdmin;
    }
    public String getIsCommerical() {
        return isCommerical;
    }
    public void setIsCommerical(String isCommerical) {
        this.isCommerical = isCommerical;
    }
    
    

    
}

