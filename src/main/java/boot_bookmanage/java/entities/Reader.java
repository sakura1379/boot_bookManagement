package boot_bookmanage.java.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author Zenglr
 * @ClassName
 * @Description
 * @create 2020-11-22-6:38 下午
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Reader implements Serializable {
    private int rid;
    private String name;
    private String role;
    private String department;
    private String email;
    private String mobile;

    @Override
    public String toString() {
        return "Reader{" +
                "rid=" + rid +
                ", name='" + name + '\'' +
                ", role='" + role + '\'' +
                ", department='" + department + '\'' +
                ", email='" + email + '\'' +
                ", mobile='" + mobile + '\'' +
                '}';
    }
}
