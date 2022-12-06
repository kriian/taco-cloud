package ru.hehnev.tacocloud.models.taco;

import lombok.Data;
import org.hibernate.validator.constraints.CreditCardNumber;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
public class TacoOrder implements Serializable {

    private static final long serialVersionUID = 1L;
    private Long id;
    private Date placedAt;

    @NotBlank(message="Укажите ваше имя")
    private String deliveryName;

    @NotBlank(message="Укажите улицу")
    private String deliveryStreet;

    @NotBlank(message="Укажите город")
    private String deliveryCity;

    @NotBlank(message="Укадите № кв")
    private String deliveryState;

    @NotBlank(message="Укажите почтовый адрес")
    private String deliveryZip;

    @CreditCardNumber(message="Недействительный номер кредитной карты")
    private String ccNumber;

    @Pattern(regexp="^(0[1-9]|1[0-2])([\\/])([2-9][0-9])$", message="Не правильный формат, ММ/ГГ")
    private String ccExpiration;

    @Digits(integer=3, fraction=0, message="Укажите три цифры на обратной стороне карты CVV")
    private String ccCVV;
    private List<Taco> tacos = new ArrayList<>();

    public void addTaco(Taco taco) {
        this.tacos.add(taco);
    }
}
