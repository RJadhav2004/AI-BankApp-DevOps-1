package com.example.bankapp.controller;

import java.math.BigDecimal;

import com.example.bankapp.model.Account;
import com.example.bankapp.service.AccountService;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * Controller for handling banking operations.
 */
@Controller
public final class BankController {

    /**
     * Service for account operations.
     */
    private final AccountService accountService;

    /**
     * Creates a new bank controller.
     *
     * @param service account service
     */
    public BankController(final AccountService service) {
        this.accountService = service;
    }

    /**
     * Displays the login page.
     *
     * @return login view
     */
    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

    /**
     * Displays the registration page.
     *
     * @return register view
     */
    @GetMapping("/register")
    public String registerPage() {
        return "register";
    }

    /**
     * Registers a new account.
     *
     * @param username user name
     * @param password password
     * @param model model object
     * @return view name
     */
    @PostMapping("/register")
    public String register(
            @RequestParam final String username,
            @RequestParam final String password,
            final Model model) {

        if (accountService.registerAccount(username, password)) {
            return "redirect:/login?registered";
        }

        model.addAttribute("error", true);
        return "register";
    }

    /**
     * Displays the dashboard.
     *
     * @param account authenticated account
     * @param model model object
     * @return dashboard view
     */
    @GetMapping("/dashboard")
    public String dashboard(
            @AuthenticationPrincipal final Account account,
            final Model model) {

        model.addAttribute("account", account);
        return "dashboard";
    }

    /**
     * Deposits money into an account.
     *
     * @param account authenticated account
     * @param amount deposit amount
     * @param redirectAttributes redirect attributes
     * @return redirect view
     */
    @PostMapping("/deposit")
    public String deposit(
            @AuthenticationPrincipal final Account account,
            @RequestParam final BigDecimal amount,
            final RedirectAttributes redirectAttributes) {

        accountService.deposit(account, amount);
        return "redirect:/dashboard";
    }

    /**
     * Withdraws money from an account.
     *
     * @param account authenticated account
     * @param amount withdrawal amount
     * @param redirectAttributes redirect attributes
     * @return redirect view
     */
    @PostMapping("/withdraw")
    public String withdraw(
            @AuthenticationPrincipal final Account account,
            @RequestParam final BigDecimal amount,
            final RedirectAttributes redirectAttributes) {

        if (!accountService.withdraw(account, amount)) {
            redirectAttributes.addFlashAttribute(
                    "error",
                    "Insufficient funds.");
        }

        return "redirect:/dashboard";
    }

    /**
     * Transfers money to another account.
     *
     * @param account authenticated account
     * @param toUsername destination username
     * @param amount transfer amount
     * @param redirectAttributes redirect attributes
     * @return redirect view
     */
    @PostMapping("/transfer")
    public String transfer(
            @AuthenticationPrincipal final Account account,
            @RequestParam final String toUsername,
            @RequestParam final BigDecimal amount,
            final RedirectAttributes redirectAttributes) {

        final String error =
                accountService.transferAmount(
                        account,
                        toUsername,
                        amount);

        if (error != null) {
            redirectAttributes.addFlashAttribute(
                    "error",
                    error);
        }

        return "redirect:/dashboard";
    }

    /**
     * Displays transaction history.
     *
     * @param account authenticated account
     * @param model model object
     * @return transactions view
     */
    @GetMapping("/transactions")
    public String transactions(
            @AuthenticationPrincipal final Account account,
            final Model model) {

        model.addAttribute(
                "transactions",
                accountService.getTransactionHistory(account));

        return "transactions";
    }
}
