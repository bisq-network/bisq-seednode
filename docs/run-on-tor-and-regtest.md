## Run on regtest

    bisq-seednode --baseCurrencyNetwork=BTC_REGTEST --useLocalhost=true --myAddress=localhost:2002 --nodePort=2002 --appName=bisq_seed_node_localhost_2002

See [How to use Bisq with regtest](https://github.com/bisq-network/exchange/wiki/4.2.1.-How-to-use-Bisq-with-regtest-(advanced)) for more information.


## Run on Tor and RegTest

If you want to run locally a seed node via Tor you need to add your seed node's hidden service address to the SeedNodesRepository.java class. You can find the hidden service address after you started once a seed node. Start it with a placeholder address like:

    bisq-seednode --baseCurrencyNetwork=BTC_REGTEST --nodePort=8002 --myAddress=xxxxxxxx.onion:8002 --appName=bisq_seed_node_xxxxxxxx.onion_8000

Once the hidden service is published (check console output) quit the seed node and copy the hidden service address from the console output. Alternatively you can navigate to the application directory and open bisq_seed_node_xxxxxxx.onion_8002/tor/hiddenservice/hostname. Use that hidden service address also to rename the xxxxxxx placeholder of your bisq_seed_node_xxxxxxx.onion_8002 directory. Start `bisq-seednode` again now with the correct hidden service address. Instructions are also at the SeedNodesRepository class in the bisq-network/exchange repository.

Here are example program arguments for using regtest and using the Tor network (example onion address is ewdkppp3vicnbgqt):

    bisq-seednode ewdkppp3vicnbgqt.onion:8002 2 50

    bisq-seednode --baseCurrencyNetwork=BTC_REGTEST --nodePort=8002 --myAddress=ewdkppp3vicnbgqt.onion:8002 --appName=bisq_seed_node_ewdkppp3vicnbgqt.onion_8002
