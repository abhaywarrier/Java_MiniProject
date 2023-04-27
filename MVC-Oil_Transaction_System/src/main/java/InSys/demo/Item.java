package InSys.demo;

import java.util.UUID;

import com.vaadin.flow.component.template.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Item {
    @Id
    @EqualsAndHashCode.Include
    private String id;
    private String name;
    private int quantity;

    private static String defaultUserName;

    public static void setDefaultValues(String userName) {
        defaultUserName = userName;
    }

    public Item(String name, int quantity) {
        this.id = UUID.randomUUID().toString();
        this.name = defaultUserName;
        this.quantity = quantity;
    }
}
