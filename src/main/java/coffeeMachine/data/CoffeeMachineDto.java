package coffeeMachine.data;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;

/**
 * DTo for coffee machine creation
 *
 * @author Virender Bhargav
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@ToString
@Setter
@Getter
@NoArgsConstructor
@Slf4j
public class CoffeeMachineDto {
    Outlet outlets;
    Map<String, Integer> total_items_quantity;
    Map<String, Map<String, Integer>> beverages;


    @JsonIgnoreProperties(ignoreUnknown = true)
    @ToString
    @Setter
    @Getter
    @NoArgsConstructor
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class Outlet {
        int count_n;
    }


}
