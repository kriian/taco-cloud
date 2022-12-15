package ru.hehnev.tacocloud.models.taco;

import lombok.Data;
import org.hibernate.validator.constraints.CreditCardNumber;

import javax.persistence.*;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@Entity(name = "taco_order")
public class TacoOrder implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "placed_at")
    private Date placedAt = new Date();

    @Column(name = "delivery_name")
    @NotBlank(message="Укажите ваше имя")
    private String deliveryName;

    @Column(name = "delivery_street")
    @NotBlank(message="Укажите улицу")
    private String deliveryStreet;

    @Column(name = "delivery_city")
    @NotBlank(message="Укажите город")
    private String deliveryCity;

    @Column(name = "delivery_state")
    @NotBlank(message="Укажите № кв")
    private String deliveryState;

    @Column(name = "delivery_zip")
    @NotBlank(message="Укажите почтовый адрес")
    private String deliveryZip;

    @Column(name = "cc_number")
    @CreditCardNumber(message="Недействительный номер кредитной карты")
    private String ccNumber;

    @Column(name = "cc_expiration")
    @Pattern(regexp="^(0[1-9]|1[0-2])([\\/])([2-9][0-9])$", message="Не правильный формат, ММ/ГГ")
    private String ccExpiration;

    @Column(name = "cc_cvv")
    @Digits(integer=3, fraction=0, message="Укажите три цифры на обратной стороне карты CVV")
    private String ccCVV;

    @OneToMany(cascade = CascadeType.ALL)
    private List<Taco> tacos = new ArrayList<>();

    public void addTaco(Taco taco) {
        this.tacos.add(taco);
    }
}
