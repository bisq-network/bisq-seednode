/*
 * This file is part of Bisq.
 *
 * Bisq is free software: you can redistribute it and/or modify it
 * under the terms of the GNU Affero General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or (at
 * your option) any later version.
 *
 * Bisq is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public
 * License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with Bisq. If not, see <http://www.gnu.org/licenses/>.
 */

package bisq.seednode;

import bisq.core.app.AppSetup;
import bisq.core.app.AppSetupWithP2P;
import bisq.core.app.AppSetupWithP2PAndDAO;
import bisq.core.app.BisqEnvironment;
import bisq.core.arbitration.ArbitratorManager;
import bisq.core.btc.wallet.BsqWalletService;
import bisq.core.btc.wallet.BtcWalletService;
import bisq.core.btc.wallet.WalletsSetup;
import bisq.core.dao.DaoOptionKeys;
import bisq.core.offer.OpenOfferManager;
import bisq.core.setup.CoreSetup;

import bisq.network.p2p.P2PService;

import bisq.common.UserThread;
import bisq.common.app.Capabilities;
import bisq.common.handlers.ResultHandler;
import bisq.common.setup.CommonSetup;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Key;
import com.google.inject.name.Names;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SeedNode {
    public static final String VERSION = "0.7.0-SNAPSHOT";

    private static BisqEnvironment bisqEnvironment;

    public static void setEnvironment(BisqEnvironment bisqEnvironment) {
        SeedNode.bisqEnvironment = bisqEnvironment;
    }

    private final Injector injector;
    private final SeedNodeModule seedNodeModule;
    private final AppSetup appSetup;

    public SeedNode() {
        CommonSetup.setup((throwable, doShutDown) -> {
            log.error(throwable.toString());
        });
        CoreSetup.setup(bisqEnvironment);
        // We add extra capability for seed node
        Capabilities.addCapability(Capabilities.Capability.SEED_NODE.ordinal());

        log.info("SeedNode.VERSION: " + VERSION);

        seedNodeModule = new SeedNodeModule(bisqEnvironment);
        injector = Guice.createInjector(seedNodeModule);

        Boolean fullDaoNode = injector.getInstance(Key.get(Boolean.class, Names.named(DaoOptionKeys.FULL_DAO_NODE)));
        appSetup = fullDaoNode ? injector.getInstance(AppSetupWithP2PAndDAO.class) : injector.getInstance(AppSetupWithP2P.class);
        appSetup.start();
    }

    private void shutDown() {
        gracefulShutDown(() -> {
            log.debug("Shutdown complete");
            System.exit(0);
        });
    }

    public void gracefulShutDown(ResultHandler resultHandler) {
        log.debug("gracefulShutDown");
        try {
            if (injector != null) {
                injector.getInstance(ArbitratorManager.class).shutDown();
                injector.getInstance(OpenOfferManager.class).shutDown(() -> injector.getInstance(P2PService.class).shutDown(() -> {
                    injector.getInstance(WalletsSetup.class).shutDownComplete.addListener((ov, o, n) -> {
                        seedNodeModule.close(injector);
                        log.debug("Graceful shutdown completed");
                        resultHandler.handleResult();
                    });
                    injector.getInstance(WalletsSetup.class).shutDown();
                    injector.getInstance(BtcWalletService.class).shutDown();
                    injector.getInstance(BsqWalletService.class).shutDown();
                }));
                // we wait max 5 sec.
                UserThread.runAfter(resultHandler::handleResult, 5);
            } else {
                UserThread.runAfter(resultHandler::handleResult, 1);
            }
        } catch (Throwable t) {
            log.debug("App shutdown failed with exception");
            t.printStackTrace();
            System.exit(1);
        }
    }
}
