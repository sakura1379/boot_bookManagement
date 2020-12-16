package boot_bookmanage.java.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;


/**
 * @author Zenglr
 * @ClassName
 * @Description
 * @create 2020-11-22-6:50 下午
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Borrow implements Serializable {
    private int id;
    private String bname;
    private String name;
    private String borrow_date;
}
