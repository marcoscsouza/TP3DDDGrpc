package com.marcoscsouza.DDDTP3.application.grpc;


import com.marcoscsouza.DDDTP3.application.service.ProductServiceImpl;
import com.marcoscsouza.DDDTP3.domains.models.Product;
import net.devh.boot.grpc.server.service.GrpcService;

import java.util.List;

@GrpcService
public class ProductGrpcService extends ProductServiceImpl {

    private final ProductServiceImpl productService;

    public ProductGrpcService(ProductServiceImpl productService) {
        this.productService = productService;
    }

    @Override
    public void createProduct(CreateProductRequest request, StreamObserver<ProductResponse> responseObserver) {
        Product product = new Product();
        product.setName(request.getName());
        product.setPrice(request.getPrice());
        Product savedProduct = productService.createProduct(product);
        ProductResponse response = ProductResponse.newBuilder().setProduct(convertToGrpcProduct(savedProduct)).build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void getProductById(GetProductRequest request, StreamObserver<ProductResponse> responseObserver) {
        productService.getProductById(request.getId()).ifPresentOrElse(
                product -> {
                    ProductResponse response = ProductResponse.newBuilder().setProduct(convertToGrpcProduct(product)).build();
                    responseObserver.onNext(response);
                    responseObserver.onCompleted();
                },
                responseObserver::onError
        );
    }

    @Override
    public void getAllProducts(Empty request, StreamObserver<ProductsResponse> responseObserver) {
        List<Product> products = productService.getAllProducts();
        ProductsResponse.Builder responseBuilder = ProductsResponse.newBuilder();
        products.forEach(product -> responseBuilder.addProducts(convertToGrpcProduct(product)));
        responseObserver.onNext(responseBuilder.build());
        responseObserver.onCompleted();
    }

    @Override
    public void updateProduct(UpdateProductRequest request, StreamObserver<ProductResponse> responseObserver) {
        Product product = new Product();
        product.setId(request.getId());
        product.setName(request.getName());
        product.setPrice(request.getPrice());
        Product updatedProduct = productService.updateProduct(request.getId(), product);
        ProductResponse response = ProductResponse.newBuilder().setProduct(convertToGrpcProduct(updatedProduct)).build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void deleteProduct(DeleteProductRequest request, StreamObserver<Empty> responseObserver) {
        productService.deleteProduct(request.getId());
        responseObserver.onNext(Empty.newBuilder().build());
        responseObserver.onCompleted();
    }

    private Product convertToGrpcProduct(Product product) {
        return Product.newBuilder()
                .setId(product.getId())
                .setName(product.getName())
                .setPrice(product.getPrice())
                .build();
    }
}
