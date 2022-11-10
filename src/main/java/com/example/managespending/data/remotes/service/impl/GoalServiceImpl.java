package com.example.managespending.data.remotes.service.impl;

import com.example.managespending.data.mapper.GoalMapper;
import com.example.managespending.data.models.dto.AccountDTO;
import com.example.managespending.data.models.dto.GoalDTO;
import com.example.managespending.data.models.dto.base.BaseDTO;
import com.example.managespending.data.models.dto.base.ResponseDTO;
import com.example.managespending.data.models.entities.Account;
import com.example.managespending.data.models.entities.Goal;
import com.example.managespending.data.models.entities.History;
import com.example.managespending.data.models.entities.Wallet;
import com.example.managespending.data.remotes.repositories.AccountRepository;
import com.example.managespending.data.remotes.repositories.GoalRepository;
import com.example.managespending.data.remotes.repositories.HistoryRepository;
import com.example.managespending.data.remotes.repositories.WalletRepository;
import com.example.managespending.data.remotes.service.GoalService;
import com.example.managespending.data.remotes.service.base.BaseService;
import com.example.managespending.utils.ResponseCode;
import com.example.managespending.utils.enums.HistoryType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class GoalServiceImpl extends BaseService<BaseDTO> implements GoalService {

    @Autowired
    GoalRepository goalRepository;

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    HistoryRepository historyRepository;

    @Autowired
    WalletRepository walletRepository;

    @Autowired
    GoalMapper mapper;

    @Override
    public ResponseDTO<BaseDTO> create(BaseDTO baseDTO) {

        try{

            if(((GoalDTO) baseDTO).getGoalName().length() == 0 ||
                    ((GoalDTO) baseDTO).getGoalEndDate() == null ||
                    ((GoalDTO) baseDTO).getGoalFinalCost() == null ||
                    ((GoalDTO) baseDTO).getAccount() == null ||
                    ((GoalDTO) baseDTO).getGoalFinalCost().compareTo(BigDecimal.ZERO) == 0){

                return ResponseDTO.<BaseDTO>builder()
                        .message("Please input name or end date or account or final cost for goal entity !")
                        .statusCode(ResponseCode.RESPONSE_ERROR_SERVER_ERROR)
                        .createdTime(LocalDateTime.now())
                        .build();

            }

            Goal goal = mapper.mapToEntity((GoalDTO) baseDTO, Goal.class);

            History history = new History();
            history.setAccount(accountRepository.findAccountByAccountUsername(((GoalDTO) baseDTO).getAccount().getAccountUsername()));
            history.setHistoryType(HistoryType.CREATE);
            history.setHistoryNote("Created new goal name " + goal.getGoalName());

            goal.setAccount(accountRepository.findAccountByAccountUsername(((GoalDTO) baseDTO).getAccount().getAccountUsername()));
            goal.setGoalPresentCost(BigDecimal.valueOf(0.0));
            goal.setGoalStatus(false);

            goalRepository.save(goal);

            history.setGoal(goal);
            historyRepository.save(history);


            return ResponseDTO.<BaseDTO>builder()
                    .message("Create goal complete !!!")
                    .statusCode(ResponseCode.RESPONSE_CREATED)
                    .createdTime(LocalDateTime.now())
                    .object(mapper.mapToDTO(goal, GoalDTO.class))
                    .build();

        }catch (Exception e){

            e.printStackTrace();

            return ResponseDTO.<BaseDTO>builder()
                    .message("Create goal fail !!!")
                    .statusCode(ResponseCode.RESPONSE_ERROR_SERVER_ERROR)
                    .createdTime(LocalDateTime.now())
                    .build();

        }

    }

    @Override
    public ResponseDTO<BaseDTO> update(BaseDTO baseDTO) {

        try{

            if(((GoalDTO) baseDTO).getGoalName().length() == 0 ||
                    ((GoalDTO) baseDTO).getGoalEndDate() == null ||
                    ((GoalDTO) baseDTO).getGoalFinalCost() == null ||
                    ((GoalDTO) baseDTO).getAccount() == null ||
                    ((GoalDTO) baseDTO).getGoalFinalCost().compareTo(BigDecimal.ZERO) == 0){

                return ResponseDTO.<BaseDTO>builder()
                        .message("Please input name or end date or account or final cost for goal entity !")
                        .statusCode(ResponseCode.RESPONSE_ERROR_SERVER_ERROR)
                        .createdTime(LocalDateTime.now())
                        .build();

            }

            Optional<Goal> opt = goalRepository.findById(((GoalDTO) baseDTO).getGoalId());

            if(!opt.isPresent()){

                return ResponseDTO.<BaseDTO>builder()
                        .message("Goal object is not exist !")
                        .statusCode(ResponseCode.RESPONSE_ERROR_SERVER_ERROR)
                        .createdTime(LocalDateTime.now())
                        .build();

            }

            Goal goal = opt.get();

            History history = new History();
            history.setAccount(accountRepository.findAccountByAccountUsername(((GoalDTO) baseDTO).getAccount().getAccountUsername()));
            history.setHistoryType(HistoryType.UPDATE);
            history.setHistoryNote("Updated goal name " + ((GoalDTO)baseDTO).getGoalName());

            goal.setAccount(accountRepository.findAccountByAccountUsername(((GoalDTO) baseDTO).getAccount().getAccountUsername()));
            goal.setGoalFinalCost((((GoalDTO) baseDTO).getGoalFinalCost()));
            goal.setGoalEndDate(((GoalDTO) baseDTO).getGoalEndDate());
            goal.setGoalColor(((GoalDTO) baseDTO).getGoalColor());
            goal.setGoalIcon(((GoalDTO) baseDTO).getGoalIcon());
            goal.setGoalName(((GoalDTO) baseDTO).getGoalName());
            goal.setGoalPresentCost(((GoalDTO) baseDTO).getGoalPresentCost());
            goal.setGoalStatus(goal.getGoalPresentCost().compareTo(goal.getGoalFinalCost()) >= 0);

            goalRepository.save(goal);

            history.setGoal(goal);
            historyRepository.save(history);


            return ResponseDTO.<BaseDTO>builder()
                    .message("Update goal complete !!!")
                    .statusCode(ResponseCode.RESPONSE_OK_CODE)
                    .createdTime(LocalDateTime.now())
                    .object(mapper.mapToDTO(goal, GoalDTO.class))
                    .build();

        }catch (Exception e){

            e.printStackTrace();

            return ResponseDTO.<BaseDTO>builder()
                    .message("Update goal fail !!!")
                    .statusCode(ResponseCode.RESPONSE_ERROR_SERVER_ERROR)
                    .createdTime(LocalDateTime.now())
                    .build();

        }

    }

    @Override
    public ResponseDTO<BaseDTO> depositMoney(BaseDTO baseDTO) {

        try{

            Optional<Wallet> walletOpt = walletRepository.findById(((GoalDTO) baseDTO).getWalletId());
            Optional<Goal> goalOpt = goalRepository.findById(((GoalDTO) baseDTO).getGoalId());

            if(!walletOpt.isPresent()){
                return ResponseDTO.<BaseDTO>builder()
                        .message("Wallet is not exist !!!")
                        .statusCode(ResponseCode.RESPONSE_ERROR_SERVER_ERROR)
                        .createdTime(LocalDateTime.now())
                        .build();
            }else if(!goalOpt.isPresent()){
                return ResponseDTO.<BaseDTO>builder()
                        .message("Goal is not exist !!!")
                        .statusCode(ResponseCode.RESPONSE_ERROR_SERVER_ERROR)
                        .createdTime(LocalDateTime.now())
                        .build();
            }

            Wallet wallet = walletOpt.get();
            Goal goal = goalOpt.get();
            Account account = accountRepository.findAccountByAccountUsername(((GoalDTO) baseDTO).getAccount().getAccountUsername());

            if(((GoalDTO) baseDTO).getGoalDepositCost().compareTo(BigDecimal.ZERO) == 0) {
                return ResponseDTO.<BaseDTO>builder()
                        .message("Please input the value of money you want to deposit !")
                        .statusCode(ResponseCode.RESPONSE_ERROR_SERVER_ERROR)
                        .createdTime(LocalDateTime.now())
                        .build();
            }

            if(wallet.getWalletBalance().compareTo(((GoalDTO) baseDTO).getGoalDepositCost()) < 0) {
                return ResponseDTO.<BaseDTO>builder()
                        .message("Wallet balance is lesser than cost you want to deposit to the goal so cannot withdraw !!!")
                        .statusCode(ResponseCode.RESPONSE_ERROR_SERVER_ERROR)
                        .createdTime(LocalDateTime.now())
                        .build();
            }

            wallet.setWalletBalance(wallet.getWalletBalance().subtract (((GoalDTO) baseDTO).getGoalDepositCost()));
            walletRepository.save(wallet);

            goal.setGoalPresentCost(goal.getGoalPresentCost().add(((GoalDTO) baseDTO).getGoalDepositCost()));

            Boolean checkGoalStatus = goal.getGoalPresentCost().compareTo(goal.getGoalFinalCost()) >= 0;
            goal.setGoalStatus(checkGoalStatus);
            goalRepository.save(goal);

            History history = new History();
            history.setGoal(goal);
            history.setWallet(wallet);
            history.setHistoryCost(((GoalDTO) baseDTO).getGoalDepositCost());
            history.setHistoryNote("Deposit money to " + goal.getGoalName() + " cost: " + ((GoalDTO) baseDTO).getGoalDepositCost());
            history.setHistoryType(HistoryType.GOAL);
            history.setAccount(account);
            historyRepository.save(history);

            if(checkGoalStatus){
                History historyGoalComplete = new History();
                historyGoalComplete.setHistoryNote("The target of " + goal.getGoalName() + " is finished !!!");
                historyGoalComplete.setHistoryCost(goal.getGoalPresentCost());
                historyGoalComplete.setHistoryType(HistoryType.GOAL);
                historyGoalComplete.setAccount(account);
                historyRepository.save(historyGoalComplete);
            }

            return ResponseDTO.<BaseDTO>builder()
                    .message("Deposit to goal complete !!!")
                    .statusCode(ResponseCode.RESPONSE_OK_CODE)
                    .createdTime(LocalDateTime.now())
                    .object(mapper.mapToDTO(goal, GoalDTO.class))
                    .build();

        }catch (Exception e){
            e.printStackTrace();
            return ResponseDTO.<BaseDTO>builder()
                    .message("Deposit to goal fail !!!")
                    .statusCode(ResponseCode.RESPONSE_ERROR_SERVER_ERROR)
                    .createdTime(LocalDateTime.now())
                    .build();
        }

    }

    @Override
    public ResponseDTO<BaseDTO> delete(BaseDTO baseDTO) {

        try{

            Optional<Goal> goalOpt = goalRepository.findById(((GoalDTO) baseDTO).getGoalId());
            if(!goalOpt.isPresent()){
                return ResponseDTO.<BaseDTO>builder()
                        .message("Goal is not exist !!!")
                        .statusCode(ResponseCode.RESPONSE_ERROR_SERVER_ERROR)
                        .createdTime(LocalDateTime.now())
                        .build();
            }

            Goal goal = goalOpt.get();
            Account account = accountRepository.findAccountByAccountUsername(((GoalDTO) baseDTO).getAccount().getAccountUsername());

            List<History> histories = historyRepository.findAllByAccountAndGoalAndHistoryType(account, goal, HistoryType.GOAL);

            histories.forEach(h -> {
                h.getWallet().setWalletBalance(h.getWallet().getWalletBalance().add(h.getHistoryCost()));
                walletRepository.save(h.getWallet());
            });

            goalRepository.delete(goal);

            History history = new History();
            history.setHistoryCost(goal.getGoalFinalCost());
            history.setHistoryNote("Complete and delete goal " + goal.getGoalName());
            history.setHistoryType(HistoryType.REMOVE);
            history.setAccount(account);
            historyRepository.save(history);

            return ResponseDTO.<BaseDTO>builder()
                    .message("Delete or finish to goal complete !!!")
                    .statusCode(ResponseCode.RESPONSE_OK_CODE)
                    .createdTime(LocalDateTime.now())
                    .build();

        }catch (Exception e){
            e.printStackTrace();
            return ResponseDTO.<BaseDTO>builder()
                    .message("Delete or finish to goal fail !!!")
                    .statusCode(ResponseCode.RESPONSE_ERROR_SERVER_ERROR)
                    .createdTime(LocalDateTime.now())
                    .build();

        }

    }

    @Override
    public ResponseDTO<BaseDTO> getOne(BaseDTO baseDTO) {

        try{

            Optional<Goal> goalOpt = goalRepository.findById(((GoalDTO) baseDTO).getGoalId());
            if(!goalOpt.isPresent()){
                return ResponseDTO.<BaseDTO>builder()
                        .message("Goal is not exist !!!")
                        .statusCode(ResponseCode.RESPONSE_ERROR_SERVER_ERROR)
                        .createdTime(LocalDateTime.now())
                        .build();
            }

            Goal goal = goalOpt.get();

            return ResponseDTO.<BaseDTO>builder()
                    .message("Get goal complete !!!")
                    .statusCode(ResponseCode.RESPONSE_OK_CODE)
                    .object(mapper.mapToDTO(goal, GoalDTO.class))
                    .createdTime(LocalDateTime.now())
                    .build();

        }catch (Exception e){
            e.printStackTrace();
            return ResponseDTO.<BaseDTO>builder()
                    .message("Get goal fail !!!")
                    .statusCode(ResponseCode.RESPONSE_ERROR_SERVER_ERROR)
                    .createdTime(LocalDateTime.now())
                    .build();

        }

    }

    @Override
    public ResponseDTO<BaseDTO> getAll(BaseDTO baseDTO) {

        try{
            Account account = accountRepository.findAccountByAccountUsername(((AccountDTO) baseDTO).getAccountUsername());
            List<Goal> goals = goalRepository.findAllByAccount(account);
            for (int i = 0; i < goals.size(); i++) {
                System.out.print(goals.indexOf(i));
            }
            return ResponseDTO.<BaseDTO>builder()
                    .message("Get goals complete !!!")
                    .statusCode(ResponseCode.RESPONSE_OK_CODE)
                    .objectList(mapper.mapToDTOList(goals, GoalDTO.class))
                    .createdTime(LocalDateTime.now())
                    .build();

        }catch (Exception e){
            e.printStackTrace();
            return ResponseDTO.<BaseDTO>builder()
                    .message("Get goals fail !!!")
                    .statusCode(ResponseCode.RESPONSE_ERROR_SERVER_ERROR)
                    .createdTime(LocalDateTime.now())
                    .build();

        }

    }

    @Override
    public ResponseDTO<BaseDTO> getByStatus(BaseDTO baseDTO) {
        try{
            if(((GoalDTO)baseDTO).getGoalStatus()==null){
                return ResponseDTO.<BaseDTO>builder()
                        .message("Please enter the goal status !!!")
                        .statusCode(ResponseCode.RESPONSE_ERROR_SERVER_ERROR)
                        .createdTime(LocalDateTime.now())
                        .build();
            }else if(((GoalDTO)baseDTO).getAccount().getAccountUsername()==null){
                return ResponseDTO.<BaseDTO>builder()
                        .message("Please enter the account_name !!!")
                        .statusCode(ResponseCode.RESPONSE_ERROR_SERVER_ERROR)
                        .createdTime(LocalDateTime.now())
                        .build();
            }else{
                Account account  = accountRepository.findAccountByAccountUsername(((GoalDTO)baseDTO).getAccount().getAccountUsername());
                if(account==null){
                    return ResponseDTO.<BaseDTO>builder()
                            .message("The account doesn't exist !!!")
                            .statusCode(ResponseCode.RESPONSE_BAD_REQUEST)
                            .createdTime(LocalDateTime.now())
                            .build();
                }else{
                    List<Goal> listGoal = goalRepository.findGoalsByAccountAndGoalStatus(account,((GoalDTO)baseDTO).getGoalStatus());
                    return ResponseDTO.<BaseDTO>builder()
                            .message("Get goals complete !!!")
                            .statusCode(ResponseCode.RESPONSE_OK_CODE)
                            .objectList(mapper.mapToDTOList(listGoal, GoalDTO.class))
                            .createdTime(LocalDateTime.now())
                            .build();
                }
            }
        }catch (Exception e){
            e.printStackTrace();
            return ResponseDTO.<BaseDTO>builder()
                    .message("Get goals fail !!!")
                    .statusCode(ResponseCode.RESPONSE_ERROR_SERVER_ERROR)
                    .createdTime(LocalDateTime.now())
                    .build();
        }
    }
}
