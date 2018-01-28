package framework;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.Bool;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.Utf8String;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.RemoteCall;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.tuples.generated.Tuple2;
import org.web3j.tuples.generated.Tuple3;
import org.web3j.tx.Contract;
import org.web3j.tx.TransactionManager;

/**
 * <p>Auto generated code.
 * <p><strong>Do not modify!</strong>
 * <p>Please use the <a href="https://docs.web3j.io/command_line.html">web3j command line tools</a>,
 * or the org.web3j.codegen.SolidityFunctionWrapperGenerator in the 
 * <a href="https://github.com/web3j/web3j/tree/master/codegen">codegen module</a> to update.
 *
 * <p>Generated with web3j version 3.2.0.
 */
public class CommunityWork extends Contract {
    private static final String BINARY = "606060405260008054600160a060020a033316600160a060020a0319909116179055610aea806100306000396000f3006060604052600436106100c45763ffffffff7c010000000000000000000000000000000000000000000000000000000060003504166312884e4481146100ce5780632f42c0171461013757806341c0e1b51461015c578063580db9a91461016f5780637b76923b1461018557806394ebbb6b146101bf5780639675c009146101de578063a42747db1461021c578063aaa9bd041461024d578063bc4b336514610263578063c45f0a401461027a578063fabf596814610285578063fd1d5ea914610322575b6001805434019055005b61013560048035600160a060020a03169060248035919060649060443590810190830135806020601f820181900481020160405190810160405281815292919060208401838380828437509496505084359460208101359450604001359250610338915050565b005b341561014257600080fd5b61014a610494565b60405190815260200160405180910390f35b341561016757600080fd5b61013561049b565b341561017a57600080fd5b61014a6004356104c2565b341561019057600080fd5b61019b6004356104e7565b60405180848152602001838152602001828152602001935050505060405180910390f35b6101ca60043561050e565b604051901515815260200160405180910390f35b34156101e957600080fd5b6101f46004356105d8565b6040519215158352901515602083015215156040808301919091526060909101905180910390f35b341561022757600080fd5b610235600435602435610605565b60405191825260208201526040908101905180910390f35b341561025857600080fd5b61014a600435610665565b6101ca600160a060020a036004351660243561067a565b6101ca600435610764565b341561029057600080fd5b61029b6004356107e0565b604051600160a060020a038216602082015260408082528190810184818151815260200191508051906020019080838360005b838110156102e65780820151838201526020016102ce565b50505050905090810190601f1680156103135780820380516001836020036101000a031916815260200191505b50935050505060405180910390f35b341561032d57600080fd5b6101ca6004356108bb565b6000818152600260208190526040909120805473ffffffffffffffffffffffffffffffffffffffff1916600160a060020a0389161781556001810187905590810185805161038a929160200190610987565b5060038181018590554284016004830155600782018054600160ff19909116811762ffff00191690915581549091908083016103c68382610a05565b5060009182526020822001849055871115905061048b576006810180544291906103f38260018301610a05565b815481106103fd57fe5b60009182526020909120015560408051908101604052600160a060020a0388168152602081018790526005820180546104398260018301610a2e565b8154811061044357fe5b90600052602060002090600202016000820151815473ffffffffffffffffffffffffffffffffffffffff1916600160a060020a03919091161781556020820151600190910155505b50505050505050565b6003545b90565b60005433600160a060020a03908116911614156104c057600054600160a060020a0316ff5b565b60006003828154811015156104d357fe5b90600052602060002090015490505b919050565b60009081526002602052604090206001810154600382015460049092015490924290910390565b60008181526002602052604081206004015481908190849042101561053257600080fd5b6000858152600260205260408120600501935091505b82548210156105d057828281548110151561055f57fe5b60009182526020909120600290910201548354600160a060020a03909116906108fc9085908590811061058e57fe5b9060005260206000209060020201600101549081150290604051600060405180830381858888f1935050505015156105c557600080fd5b600190910190610548565b505050919050565b60008181526002602052604090206007015460ff6101008204811691620100008104821691169193909250565b60008281526002602052604081206006810180548392600501919081908690811061062c57fe5b906000526020600020900154828681548110151561064657fe5b9060005260206000209060020201600101549350935050509250929050565b60009081526002602052604090206006015490565b600081815260026020526040812060060180543491429161069e8260018301610a05565b815481106106a857fe5b6000918252602080832090910192909255848152600290915260409081902060010180548301905580519081016040908152600160a060020a038616825260208083018490526000868152600290915220600501805461070b8260018301610a2e565b8154811061071557fe5b90600052602060002090600202016000820151815473ffffffffffffffffffffffffffffffffffffffff1916600160a060020a0391909116178155602082015181600101559050505092915050565b60008181526002602052604080822060078101546001909101546301000000909104600160a060020a0316916108fc821502919051600060405180830381858888f19350505050156107d857506000818152600260205260409020600701805462ff000019166201000017905560016104e2565b5060006104e2565b6107e8610a5a565b60008281526002602081815260408084206007810154908401805490946301000000909204600160a060020a0316938593610100600184161502600019019092169290920491601f8301819004810201905190810160405280929190818152602001828054600181600116156101000203166002900480156108ab5780601f10610880576101008083540402835291602001916108ab565b820191906000526020600020905b81548152906001019060200180831161088e57829003601f168201915b5050505050915091509150915091565b6000818152600260205260408120600401544211156108d957600080fd5b60008281526002602052604090206007015462010000900460ff16158015610917575060008281526002602052604090206007015460ff1615156001145b801561093a5750600082815260026020526040902060070154610100900460ff16155b156107d857506000818152600260205260409020600701805476ffffffffffffffffffffffffffffffffffffffff0000001916630100000033600160a060020a03160217905560016104e2565b828054600181600116156101000203166002900490600052602060002090601f016020900481019282601f106109c857805160ff19168380011785556109f5565b828001600101855582156109f5579182015b828111156109f55782518255916020019190600101906109da565b50610a01929150610a6c565b5090565b815481835581811511610a2957600083815260209020610a29918101908301610a6c565b505050565b815481835581811511610a2957600202816002028360005260206000209182019101610a299190610a86565b60206040519081016040526000815290565b61049891905b80821115610a015760008155600101610a72565b61049891905b80821115610a0157805473ffffffffffffffffffffffffffffffffffffffff1916815560006001820155600201610a8c5600a165627a7a723058204730ac616903fbb346d045cff5b5b4ecdc0613a55b2061b7b8bd46743727b2400029";

    protected CommunityWork(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    protected CommunityWork(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public RemoteCall<TransactionReceipt> projectDeployer(String _projectOwnerAddress, BigInteger _allocatedFunds, String _projectDescription, BigInteger _targetAmount, BigInteger _fundingDuration, BigInteger _projectId, BigInteger weiValue) {
        Function function = new Function(
                "projectDeployer", 
                Arrays.<Type>asList(new Address(_projectOwnerAddress),
                new Uint256(_allocatedFunds),
                new Utf8String(_projectDescription),
                new Uint256(_targetAmount),
                new Uint256(_fundingDuration),
                new Uint256(_projectId)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function, weiValue);
    }

    public RemoteCall<BigInteger> getProjectIdLength() {
        Function function = new Function("getProjectIdLength", 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteCall<TransactionReceipt> kill() {
        Function function = new Function(
                "kill", 
                Arrays.<Type>asList(), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<BigInteger> getAllProjectId(BigInteger _index) {
        Function function = new Function("getAllProjectId", 
                Arrays.<Type>asList(new Uint256(_index)),
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteCall<Tuple3<BigInteger, BigInteger, BigInteger>> getProjectValues(BigInteger _projectId) {
        final Function function = new Function("getProjectValues", 
                Arrays.<Type>asList(new Uint256(_projectId)),
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}, new TypeReference<Uint256>() {}, new TypeReference<Uint256>() {}));
        return new RemoteCall<Tuple3<BigInteger, BigInteger, BigInteger>>(
                new Callable<Tuple3<BigInteger, BigInteger, BigInteger>>() {
                    @Override
                    public Tuple3<BigInteger, BigInteger, BigInteger> call() throws Exception {
                        List<Type> results = executeCallMultipleValueReturn(function);;
                        return new Tuple3<BigInteger, BigInteger, BigInteger>(
                                (BigInteger) results.get(0).getValue(), 
                                (BigInteger) results.get(1).getValue(), 
                                (BigInteger) results.get(2).getValue());
                    }
                });
    }

    public RemoteCall<TransactionReceipt> endingProject(BigInteger _projectId, BigInteger weiValue) {
        Function function = new Function(
                "endingProject", 
                Arrays.<Type>asList(new Uint256(_projectId)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function, weiValue);
    }

    public RemoteCall<Tuple3<Boolean, Boolean, Boolean>> getProjectStatus(BigInteger _projectId) {
        final Function function = new Function("getProjectStatus", 
                Arrays.<Type>asList(new Uint256(_projectId)),
                Arrays.<TypeReference<?>>asList(new TypeReference<Bool>() {}, new TypeReference<Bool>() {}, new TypeReference<Bool>() {}));
        return new RemoteCall<Tuple3<Boolean, Boolean, Boolean>>(
                new Callable<Tuple3<Boolean, Boolean, Boolean>>() {
                    @Override
                    public Tuple3<Boolean, Boolean, Boolean> call() throws Exception {
                        List<Type> results = executeCallMultipleValueReturn(function);;
                        return new Tuple3<Boolean, Boolean, Boolean>(
                                (Boolean) results.get(0).getValue(), 
                                (Boolean) results.get(1).getValue(), 
                                (Boolean) results.get(2).getValue());
                    }
                });
    }

    public RemoteCall<Tuple2<BigInteger, BigInteger>> pointForPlot(BigInteger _projectId, BigInteger i) {
        final Function function = new Function("pointForPlot", 
                Arrays.<Type>asList(new Uint256(_projectId),
                new Uint256(i)),
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}, new TypeReference<Uint256>() {}));
        return new RemoteCall<Tuple2<BigInteger, BigInteger>>(
                new Callable<Tuple2<BigInteger, BigInteger>>() {
                    @Override
                    public Tuple2<BigInteger, BigInteger> call() throws Exception {
                        List<Type> results = executeCallMultipleValueReturn(function);;
                        return new Tuple2<BigInteger, BigInteger>(
                                (BigInteger) results.get(0).getValue(), 
                                (BigInteger) results.get(1).getValue());
                    }
                });
    }

    public RemoteCall<BigInteger> numberOfPoints(BigInteger _projectId) {
        Function function = new Function("numberOfPoints", 
                Arrays.<Type>asList(new Uint256(_projectId)),
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteCall<TransactionReceipt> addFunds(String _senderAddress, BigInteger _projectId, BigInteger weiValue) {
        Function function = new Function(
                "addFunds", 
                Arrays.<Type>asList(new Address(_senderAddress),
                new Uint256(_projectId)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function, weiValue);
    }

    public RemoteCall<TransactionReceipt> onCompletion(BigInteger _projectId, BigInteger weiValue) {
        Function function = new Function(
                "onCompletion", 
                Arrays.<Type>asList(new Uint256(_projectId)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function, weiValue);
    }

    public RemoteCall<Tuple2<String, String>> getProjectInfo(BigInteger _projectId) {
        final Function function = new Function("getProjectInfo", 
                Arrays.<Type>asList(new Uint256(_projectId)),
                Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}, new TypeReference<Address>() {}));
        return new RemoteCall<Tuple2<String, String>>(
                new Callable<Tuple2<String, String>>() {
                    @Override
                    public Tuple2<String, String> call() throws Exception {
                        List<Type> results = executeCallMultipleValueReturn(function);;
                        return new Tuple2<String, String>(
                                (String) results.get(0).getValue(), 
                                (String) results.get(1).getValue());
                    }
                });
    }

    public RemoteCall<TransactionReceipt> acceptProject(BigInteger _projectId) {
        Function function = new Function(
                "acceptProject", 
                Arrays.<Type>asList(new Uint256(_projectId)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public static RemoteCall<CommunityWork> deploy(Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit, BigInteger initialWeiValue) {
        return deployRemoteCall(CommunityWork.class, web3j, credentials, gasPrice, gasLimit, BINARY, "", initialWeiValue);
    }

    public static RemoteCall<CommunityWork> deploy(Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit, BigInteger initialWeiValue) {
        return deployRemoteCall(CommunityWork.class, web3j, transactionManager, gasPrice, gasLimit, BINARY, "", initialWeiValue);
    }

    public static CommunityWork load(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new CommunityWork(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    public static CommunityWork load(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new CommunityWork(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }
}
