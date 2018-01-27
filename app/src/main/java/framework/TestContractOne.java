package framework;

import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.Utf8String;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.RemoteCall;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.tuples.generated.Tuple2;
import org.web3j.tx.Contract;
import org.web3j.tx.TransactionManager;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;

/**
 * <p>Auto generated code.
 * <p><strong>Do not modify!</strong>
 * <p>Please use the <a href="https://docs.web3j.io/command_line.html">web3j command line tools</a>,
 * or the org.web3j.codegen.SolidityFunctionWrapperGenerator in the 
 * <a href="https://github.com/web3j/web3j/tree/master/codegen">codegen module</a> to update.
 *
 * <p>Generated with web3j version 3.2.0.
 */
public class TestContractOne extends Contract {
    private static final String BINARY = "6060604052341561000f57600080fd5b6102dc8061001e6000396000f30060606040526004361061004b5763ffffffff7c0100000000000000000000000000000000000000000000000000000000600035041663993a04b78114610050578063f5dce133146100e2575b600080fd5b341561005b57600080fd5b610063610137565b6040518080602001838152602001828103825284818151815260200191508051906020019080838360005b838110156100a657808201518382015260200161008e565b50505050905090810190601f1680156100d35780820380516001836020036101000a031916815260200191505b50935050505060405180910390f35b34156100ed57600080fd5b61013560046024813581810190830135806020601f8201819004810201604051908101604052818152929190602084018383808284375094965050933593506101e992505050565b005b61013f610203565b600080600154818054600181600116156101000203166002900480601f0160208091040260200160405190810160405280929190818152602001828054600181600116156101000203166002900480156101da5780601f106101af576101008083540402835291602001916101da565b820191906000526020600020905b8154815290600101906020018083116101bd57829003601f168201915b50505050509150915091509091565b60008280516101fc929160200190610215565b5060015550565b60206040519081016040526000815290565b828054600181600116156101000203166002900490600052602060002090601f016020900481019282601f1061025657805160ff1916838001178555610283565b82800160010185558215610283579182015b82811115610283578251825591602001919060010190610268565b5061028f929150610293565b5090565b6102ad91905b8082111561028f5760008155600101610299565b905600a165627a7a7230582053c79e8692bf88a3e4d1f4328f7bd1cf03bfd6e41032816c60102e6f7f4f669d0029";

    protected TestContractOne(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    protected TestContractOne(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public RemoteCall<Tuple2<String, BigInteger>> getter() {
        final Function function = new Function("getter", 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}, new TypeReference<Uint256>() {}));
        return new RemoteCall<Tuple2<String, BigInteger>>(
                new Callable<Tuple2<String, BigInteger>>() {
                    @Override
                    public Tuple2<String, BigInteger> call() throws Exception {
                        List<Type> results = executeCallMultipleValueReturn(function);;
                        return new Tuple2<String, BigInteger>(
                                (String) results.get(0).getValue(), 
                                (BigInteger) results.get(1).getValue());
                    }
                });
    }

    public RemoteCall<TransactionReceipt> setter(String _name, BigInteger _age) {
        Function function = new Function(
                "setter", 
                Arrays.<Type>asList(new Utf8String(_name),
                new Uint256(_age)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public static RemoteCall<TestContractOne> deploy(Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(TestContractOne.class, web3j, credentials, gasPrice, gasLimit, BINARY, "");
    }

    public static RemoteCall<TestContractOne> deploy(Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(TestContractOne.class, web3j, transactionManager, gasPrice, gasLimit, BINARY, "");
    }

    public static TestContractOne load(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new TestContractOne(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    public static TestContractOne load(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new TestContractOne(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }
}
