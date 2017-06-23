package com.orion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.ThreadLocalRandom;
@Controller
public class ATMController {
    private final UserRepository userRepository;
    private final TransactionRepository transactionRepository;

    @Autowired
    public ATMController(TransactionRepository transactionRepository, UserRepository userRepository) {
        this.transactionRepository = transactionRepository;
        this.userRepository = userRepository;
    }

    @RequestMapping("/")
    public String goToLogin(Model model) {
        model.addAttribute("user", new User());
        model.addAttribute("transaction", new Transaction());
        return "login";
    }

    @RequestMapping("/login")
    public String login(@Valid User user, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "login";
        }
        if (userRepository.existsByAccountNumber(user.getAccountNumber())) {
            user = userRepository.findIdByAccountNumber(user.getAccountNumber());
            model.addAttribute("user", user);
            model.addAttribute("transaction", new Transaction());
            model.addAttribute("message", "<h2>Welcome User!</h2>");
            return "home";
        } else {
            model.addAttribute("user", new User());
            model.addAttribute("transaction", new Transaction());
            return "login";
        }
    }

    @RequestMapping("/create")
    public String createAccount(@Valid Transaction transaction, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "login";
        }
        User user = new User();
        user.setAccountNumber(ThreadLocalRandom.current().nextInt(1000, 9999 + 1));
        user.setBalance(transaction.getAmount());
        userRepository.save(user);
        model.addAttribute("user", user);
        model.addAttribute("transaction", new Transaction());
        model.addAttribute("message", "<h2>Welcome User!</h2>");
        return "home";
    }

    @RequestMapping("/transaction")
    public String transaction(@Valid Transaction transaction, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "home";
        }
        User user = userRepository.findOne(transaction.getUserId());
        transaction.setTimeStamp(new Date());
        int balance = user.getBalance();
        //do transaction
        if (transaction.getAction().equals("deposit"))
            user.setBalance(user.getBalance() + transaction.getAmount());
        else user.setBalance(user.getBalance() - transaction.getAmount());

        String format = "<h2>Starting balance: $%s.00, after making a %s of $%s.00 you now have $%s.00</h2>";
        String message = String.format(format, balance, transaction.getAction(), transaction.getAmount(), user.getBalance());

        transactionRepository.save(transaction);
        userRepository.save(user);

        model.addAttribute("user", user);
        model.addAttribute("transaction", new Transaction());
        model.addAttribute("message", message);

        return "home";
    }

    @RequestMapping("/balance")
    public String getBalanceMessage(@Valid Transaction transaction, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "home";
        }
        User user = userRepository.findOne(transaction.getUserId());
        int balance = user.getBalance();
        String message = String.format("<h2>Your balance is: $%s.00</h2>", balance);


        model.addAttribute("user", user);
        model.addAttribute("transaction", new Transaction());
        model.addAttribute("message", message);
        return "home";
    }

    @RequestMapping("/history")
    public String getHistoryMessage(@Valid Transaction transaction, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "home";
        }
        User user = userRepository.findOne(transaction.getUserId());
        ArrayList<Transaction> list = transactionRepository.findAllByUserIdOrderByTimeStamp(transaction.getUserId());
        String message = buildHistory(list);
        model.addAttribute("user", user);
        model.addAttribute("transaction", new Transaction());
        model.addAttribute("message", message);
        return "home";
    }

    @RequestMapping("/logout")
    public String logout(Model model) {
        model.addAttribute("user", new User());
        model.addAttribute("transaction", new Transaction());
        return "login";
    }

    public static String buildHistory(ArrayList<Transaction> list) {
        String returnString = "";
        for (Transaction t : list)
            returnString += String.format("<p>On %s %s %s $%s.00 reason: %s</p>", t.getTimeStamp(), t.getUserId(), t.getAction(), t.getAmount(), t.getReason());
        return returnString;
    }
    //todo add log out, add check balance, add validation



}