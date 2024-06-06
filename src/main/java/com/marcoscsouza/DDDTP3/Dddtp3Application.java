package com.marcoscsouza.DDDTP3;

import com.marcoscsouza.DDDTP3.application.grpc.ProductGrpcService;
import io.grpc.Server;
import io.grpc.netty.shaded.io.grpc.netty.GrpcSslContexts;
import io.grpc.netty.shaded.io.grpc.netty.NettyServerBuilder;
import io.grpc.netty.shaded.io.netty.handler.ssl.SslContext;
import io.grpc.protobuf.services.ProtoReflectionService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.File;

@SpringBootApplication
public class Dddtp3Application {

	public static void main(String[] args) throws InterruptedException {

		SpringApplication.run(Dddtp3Application.class, args);

		// Caminhos para os arquivos de certificado e chave
		File certChainFile = new File("src/main/resources/certificates/server.crt");
		File privateKeyFile = new File("src/main/resources/certificates/server.pem");

		// Configurar o contexto SSL
		SslContext sslContext = GrpcSslContexts.forServer(certChainFile, privateKeyFile).build();

		// Construir o servidor gRPC com TLS
		Server server = NettyServerBuilder.forPort(9091)
				.sslContext(sslContext)
				.addService(new ProductGrpcService())
				.addService(ProtoReflectionService.newInstance())
				.build()
				.start();

		// Aguarde a terminação do servidor (opcional)
		server.awaitTermination();
	}

}
