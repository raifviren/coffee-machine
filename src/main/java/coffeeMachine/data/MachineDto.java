package coffeeMachine.data;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

/**
 * Dto to parse create machine json
 * @author Virender Bhargav
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@ToString
@Setter
@Getter
@NoArgsConstructor
@Slf4j
public class MachineDto {
    CoffeeMachineDto machine;
}
