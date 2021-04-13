package edu.byu.cs.tweeter.client.model.integration;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Base64;

import edu.byu.cs.tweeter.client.model.service.LoginServiceProxy;
import edu.byu.cs.tweeter.shared.model.net.TweeterRemoteException;
import edu.byu.cs.tweeter.shared.model.request.LoginRequest;
import edu.byu.cs.tweeter.shared.model.request.LogoutRequest;
import edu.byu.cs.tweeter.shared.model.request.RegisterRequest;
import edu.byu.cs.tweeter.shared.model.response.LoginResponse;
import edu.byu.cs.tweeter.shared.model.response.LogoutResponse;
import edu.byu.cs.tweeter.shared.model.response.RegisterResponse;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class LoginServiceIntegrationTest {

    LoginServiceProxy service;
    LoginRequest loginRequestValid;
    RegisterRequest registerRequestValid;
    LogoutRequest logoutRequestValid;
    
    String base64turtle = "/9j/4AAQSkZJRgABAQAAAQABAAD/2wCEAAoHCBISFRISFBIYEhUVEhISEhESEhISFRASGBUZGRgUGBgcIS4lHB4rHxgWJjgmKy8xNTU1GiQ7QDs0Py40NTEBDAwMEA8QHhISGjEhISE0MTE0MTE0NDQ0NDQ0NDQ0NDQ0NDQ0NDQ0NDQ0NDQ0NDQ0NDQ0NDQ0ND80NDExPzQxNP/AABEIAOEA4QMBIgACEQEDEQH/xAAcAAEAAgMBAQEAAAAAAAAAAAAABQYDBAcCAQj/xABAEAACAQIDBgMFBQUGBwAAAAAAAQIDEQQFIQYSMUFRYRMicQcyQoGxFFKRodFicoLB4SMzQ1NzgzSSk6LC8PH/xAAZAQEAAwEBAAAAAAAAAAAAAAAAAQIDBAX/xAAnEQEAAgEDAwQCAwEAAAAAAAAAAQIRAyExBBJBIjJRcRORFGGBI//aAAwDAQACEQMRAD8A7MAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAADHVqKKcpOySu30RWMZtbFXUI/ORnqatdP3SLWYqlWEdZSS9WUDE7UVZfFZdEc/2l2txEpunCbgr6u+r7mMdRN5xSv7Rl3qGOpSdlON/VGxdM4Bl2fVcPWhRlXWJjOClvwUluSfwu/MuUM3qRacJSt3bKW6m+nOL1/UmXTwVXINpPFkqdT3n7sur6MtR1aepXUjMJyAAuAAAAAAAAAAAAAAAAAAAAAAAAAAArW3E5rDSjGThvPdckk2vxOfOb5nSdrqO/hpv7vm+SOYyTZ5/Ve//FbMc6mpCbQbO1qrVfDx37q1SHCSf3kTkPDi/PUS7XJDD5rh46Rmr99Dni9qTmsIhWNkNka0aqr4iPhxhfcg/enJ8324F7qUI9DDHHxeqkn8zFWzGC56mWpe+rbNlswwOTpzjJOzUk/zOq4OspwhNfFFM5BUrb79ToOw9dzoNN3UZyjH0Vjt6O0xaaz5RCygA9BYAAAAAAAAAAAAAAAAAAAAAAAAAAGOtSjOMoyV4yTTXVM5Vtts/LDz8WLk6MuV9IPo9DrJhxGHhUi4TipRkrOL4NGeppxeETGXCISpr4Ue5Spvki8517OoTbnh6nht3e5LWK9HxOfZ1leIwk9ytBxv7svhl6M47aNo5UnZlUIctPRmanGK4EJCqySwkmzO1ZjyJNOyvzfDsjpGwlHdwyf3pzfy0OZJNnWNk6e7haPeN/XuadLHr/xNU0AD0FwAAAAAAAAAAAAAAAAAAAAAAAAAAAAAIrP8np4yjOnOKbs3CVtYyto0yVA5H52xGBlSqTpy4wk4s3sPCxLbV0bYyv3nf8iPgjy7zvMM3u51/Z1Ww2H/ANOP0OPnYsg/4bD/AOlH6G3S+6VqpEAHcsA1MRj6dP3pq/3Vq/wIyvny+CPzf6GdtWleZVm9Y5lPGCriYQ96SRTc8z7E06U5wauuOj0T6GllOY+NCMm7yt5vUxt1MYzWMqTqxjZdp5rSXNv0MTzqn0l+BW94+7xhPVan9Kflssazqn0l+Blhm1J8Xu93wKvvENtLjtyChF+aemnQmvU6kz4I1LOn7y6r8T0UDKqcqdOmnJuVrtvqTeFzecLKXmXfibV6usziYwvGrCyA1sLi4VF5Xr05o2TpiYmMw1AASAAAAAAAAAAAAADku2ELYqr3dyGSLDtvG2Kl3in9SvXPK1PfP2pIWfN83qQpYOEJOEfCjdr4pW1RWDZx1bew9LXzUqkk/wB2VkvozTQneY+Vbe2XQ6O0tOlhadWrK82t3dTW9KRA43aqrVuovw4vkuLXdlCzWpJ+HLebSWivon2R7jmMYxTb1sb2taYwyve0xGJWyGK5t/NswYnPqdPnvPotSrLFVq73Y3SZNZbk8IWlPzy6PgjCaVjln2xHL3HEYnGXjGO5B8XLTQ1KVSpgq27P3Xx6Puiz05pKy0XRGvmmCjiIbr0nHWEv/FlYtvxsmLxx4bEMWpJNPR8DIsSUvCY2dCTp1LpJ2V/hfQkJ5tTj8RP4/hE5iVl+0orsp/aMUucIP6cTSxGfxs1G7b0R5yPFRg5bztOWuvcmNOYhMbRld/GHjEL9r7j7V3MuxXKchinF3Ts0WDLM8jNqE3aXBS5MoTxZhq5goJybtbU005tSdlq3mvDr59KjsZtPDEU1Cc7VIu1pfFHk7luPRraJjMOyJyAAlIAAAAAAAAAAObe0KFq8Jfeppfg2VO5ePaVR/uJ95xf4K31KJvHm60Y1JUnlkuauPqNR0dk+P8jNvGtjo70JL5lKTi0ShheIhOm4t6rVGrQowco3fE8ZZWUXrzeplzOl4bU4+63ddnzR2TG/2y42hYMNuwVoqxuwrFfweK3kjejWMrVc6ahWMvj21uQ8K5H5nmT/ALuGrZSKZlMRM8M+bYiGImoqN2tLrjLoZsNs1FpOc7fsrzWGS4PcXiS1k9V27kyqomcbVXm+NoatLIcNHlJ972NPNchut+jJtr4Xo/l1JfxB4hWJmJzlXvsqmGzCUX4c9GtLs3vtHc8bQ06c5J2tJrVrn3NTD5PiJpNSSjybkloa5rjM7LdsTGY2bFXHRjxl8iPc515JcI3SSel33JbD5DShrUqOb+7G6/NmLMKUY23FurlbkO6PCa9sTzlL4eEaMFCL10cprm+i7F+2WzxV4+HJ+eK0/bj1OYYPFb8deK0ZtYTGypTjUg7OLv8A0J05mkq1vNbZl2gGllWPjiKcKkX7y1XR80bp2u7kAAAAAAAAAAFa25wfi4WbS80Gpr0vr+SOSqZ3qtSU4yjLVSTTXZnEdoMBLC1503wUm4vrF8Dk6im8WVs1N48zd00Yt4+OZzYVRHuza7kxhpRqQdOfPg+jIjFR8zM2GqP+JfmjqicxiVLRl5tOhNxl1/8AWSdKvdXR6nCOIhuvSaXlfXsyGU50pOMk9OXQnnaeWcx3faVxWM3FZcTxlWH3peJP5IjqSdSd3wRM05pJJcCJ+IRPpjHlMRrGRViJjWMsaxn2skmqo8UjlWHjkdo1M6qXmvQkcHU8kfQgMxq3m/QlMLUtGPoXmNl7e2Eg6hrYrzRa58UY3UMcqhWIUaNKtuSUuT8sl3JCVQicWrSa5PVep7oYi8deK0L4aWjO7oHs+zncqSw8n5Z6wvymuXzv+R0w/PGHxrpyhOL80JKS9bnWs62q8CnSlCKlKcFNpv3V/wDUzalsV38NtK2K7+FtBE7O5r9qoxq7u63dSXdOxLGrcAAAAAAAAKV7Qshdemq8FedNapfFDp+ZdTy1fR6lbVi0Ykl+c3Mbxddu9kpUZSxNCLlTk7zglfclzfpxKNc4rVms4lmwYlamGN07osmWbPSrxU5y8OD4aeaSJiGyWFXFzl6tGc69K7JisyqdCrvarSS4rr3MmKqePaMYXmtHK9k10ZZK2ydDjCc4SXB3TIDFYCWHlut6t3jNaKf9S9Nal9oUtTyiZylTlZrda4o3KVdSWhtydOstyp5ZL3Z81+pEYnC1KD11i+El7r/Q3jfaWcxn7SSqHpVSNpYpPszMpk4Z4bvijxjT3w5EdphgxFS82SlKpaMfQhJy8xJRnoiZhe3ENt1Tw5mu5nlyIwph9xTur9GasZ2l2epmnK6Zpzei7EwvXh7xFXkichUqKnTjOTnOS4yd2lyiRWV4Xflvz9yGv7z6Fr2Sy54zFRv7kGpy7pcF+QnfaFojOzqOyeD8HC0YNWe65P8Aibf8yaPEIpJJaJKyXRHs3iMRh0gAJAAAAAAAAGOpBSTUkmmmmnqmjhe0tGgsbOnRW7DxN1rlvX81u3A7fj6yhTqTfwwk/wAj8+Yys3WVRvV1N5v1Zz688QrZco4pJJLRJJJdDIsaQ28N48vsg7pTEsYRubqNWEoyXK6fNPk0YVI087zDcW7HoWrT1RhEyrkcU4vdlrbRS5okcPmGm7K1SD5PUhasr3ZrwqOL0fy5HpRGVZrlP18rhPzUZa8fDb1+TI6W/Te7JNNcmeaWLa4PdZJ08xUlu1Iqou/FejJxMf2z3aUa6Z73jall9KetOe6/uy4fialbLq0PhbXWN2iMwjES1r3mbqkaC8uvPmevtJZMxlvbx5lNI1IOpPSMW/RXNyjlFaes7U1+1o/wInEcq9vzLWqYjobOGy+pOLlpGPWWl+yN2lhaFHX+8l1lwT9DXx2Yt8XbohvKfoli/LuWUFDR259zNkOY1aeIpzpzcGmlZN2cejXMj8HgK+Il/Z0pzb5Qi5WLDgtkMxg1P7NPTWzTv9CtonHpa1rh2jKc0jWir6Tsrx690SZynCSx1O2/g6t18UYSL1s9jq9WP9rSlTstJTW638iNHV1J9OpXf58NU4ADqAAAAAAAAFf21xHh4Wprbe8n4nDscuZ1n2k4i0KVP7zcremn8zleMicWrb/p9KW5TNKW9GL6pHs1ssnvU4dUrG3b8TknacIeWiB2gi0t5In/AKmti6CnFxkrrn6FqTi0SKM68mfYw5s6a/ZeqtONXDYhNSimlUXB815UQeM9nWZU72peIlzhKP8ANnpRU3VJIyQuuBJ1dmcdD3sLNfJP6M1ZZZiY8aFRf7c3/IIfKda3HTujew+McdVU+T/qaCw1X/Kn/wBOf6GWnl9efu0Jv/bn+hWYyjENvLKrtOW6mpO+tjfVdf5cfwISeXV1PclGUHyg7p+rJ3L9mZSs5za7KTMdS9a+6TsiXmWMnycY/OKNLEYxfFUv2jqWmns9h1xTfq2Y8TkFG2kbGEdTTOyfx4UqripP3Vbu+JoO7b5vmyx5hlfhpyXBEJShd36nRW8WjMJdd9j+ZKdGph5W3oS346K8oyvf8LL8TpJ+etnMXPDVYVYOzXFcpLmmdyyTNoYqmqkXZ8JR5xZrp6kT6fK8SkwAbJAAAAAAAAD42fSr7RZ1FN0Yy1+Np/kZ6urGnXukVLa7HSr1pO3lheEP5v8AIp+Kg+hbsRKL5ogcdFanmV1JtOZ8qzDBkqe7NW4S/kb0k1p1GW5bUcN9S3FJ3StxXUyVMDNfHf5FbTE2ndGHh6epjaPs4S56nkQh0T2f4vepTpv4Jafuv+pbjnfs8qNVqkeTpJ/9yOiHp9PbOnC8cB5aTPQNksTox+6vwR6jBLgkvRHsxV5WjJ8LJ6gcpzWoqmJrVHredo9orkfIVTXnSneV1xk3+Z5tJHiWnunOVW46xjnWNZ1LGKUiIqjLWziV6VT0X1K1h6XAsOY605/L6kXhoHTpzio28JTsWjZ/MZYaamvdek49UV+g0bsK8VzKTMxOYHZ8LiI1IxnF3jJXRmKtsJiXUoSXKM7RfZq/1LSenp27qxb5XAAXAAAAAAKxm2x1GvKVRVJ05S1e41Zv5lnBFqxbmBQJez2XLFy+aX6GtifZ5uRlKWJnJRi5OMYq7ty4HSD40U/DTxCMQ5SsxpJKKdlFbqXSxiqYum+aOg47ZjCVruVJJvnHy/QiJ+zzCPhKa7bz/U4v4MxxJuo1fEQ6o1XXjeyevRczoK9nOE5ym/4n+pL5dsrg6FnGkm1zn5vqXjpJ+UYQ+wOWzgp1pxcd9KMb6Nq6dy6nlK2iPR2UpFK9sLQAAsBVdsM6jRUaTe7vptvt0LUamMwFKsrVKcZ24b0U7ehTUp31mucZHNVmVF/Ej5LFUX8SLrX2PwM/8Ld/dbRqy2DwT+GS/il+pwfwZ8SjdS686fKSI6rXguaOirYPB9Jf88v1NihsZgof4e9+9JstXo7R5RhznK8qnjnKlTe7pd1JJuMbehK0/ZliOeJp/KEzpWDwNKit2nCMF0ikr+ptHTTQrWMTuntc1p+zSfxYrT9mLX1JLB+znDQ1nUqVOzcUvyReAafjp8GIauBwVOhCNOnFQhHgkbQBdIAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAP//Z";

    @BeforeEach
    void setup() {
        service = new LoginServiceProxy();
        // These are actual, real credentials in DynamoDB.
        loginRequestValid = new LoginRequest("@testUser", "test");
        // Turns out turtle friend from above into a byte[] so we can use it to register.
        byte[] turtleBytes = Base64.getDecoder().decode(base64turtle);
        // Unique unix epoch timestamp
        long usernameSuffix = System.currentTimeMillis();
        registerRequestValid = new RegisterRequest("_itu_" + usernameSuffix, "test", "IntegrationTest", "User", turtleBytes);
    }

    @Test
    void test_loginSuccess() throws IOException, TweeterRemoteException {
        LoginResponse response = service.login(loginRequestValid);
        assertTrue(response.isSuccess());
        assertEquals(response.getUser().getAlias(), "@testUser"); // That's what the dummy service is returning.
    }

    @Test
    void test_registerSuccess() throws IOException, TweeterRemoteException {
        RegisterResponse response = service.register(registerRequestValid);
        System.out.println("Registered test user " + response.getUser().getAlias());
        assertTrue(response.isSuccess());
        assertEquals(response.getUser().getAlias(), registerRequestValid.getUsername());
    }

    @Test
    void test_logoutSuccess() throws IOException, TweeterRemoteException {
        // We must log in first, so we have a token to use to log out.
        LoginResponse loginResponse = service.login(loginRequestValid);
        logoutRequestValid = new LogoutRequest(loginResponse.getAuthToken());
        LogoutResponse response = service.logout(logoutRequestValid);
        assertTrue(response.isSuccess());
    }
}
