package com.bkap.services.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.stream.Collectors;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.bkap.convert.ColorSizeProductConvert;
import com.bkap.convert.ProductConvert;
import com.bkap.convert.ProductImageConvert;
import com.bkap.dto.ColorSizeProductDto;
import com.bkap.dto.ProductDto;
import com.bkap.dto.ProductImageDto;
import com.bkap.entities.ColorSizeProduct;
import com.bkap.entities.Product;
import com.bkap.entities.ProductImage;
import com.bkap.filter.ProductFilter;
import com.bkap.repositories.ColorSizeRepository;
import com.bkap.repositories.ProductImgaeRepository;
import com.bkap.repositories.ProductRepository;
import com.bkap.services.IProductService;

@Service
public class ProductServiceImpl implements IProductService {

	@Autowired
	private ProductRepository productRepository;

	@Autowired
	private ColorSizeRepository colorSizeRepository;

	@Autowired
	private ColorSizeProductConvert convertColorSize;

	@Autowired
	private ProductImgaeRepository productImageRepository;

	@Autowired
	private ProductConvert convert;

	@Autowired
	private ProductImageConvert convertSliderImage;

	@Override
	public List<ProductDto> getAll() {
		List<Product> listPrd = productRepository.findAll();
		List<ProductDto> listPrdDto = new ArrayList<ProductDto>();
		listPrd.forEach(item -> listPrdDto.add(convert.toDto(item)));
		return listPrdDto;
	}

	@Override
	public ProductDto findOne(int productId) {
		return convert.toDto(productRepository.getOne(productId));
	}

	public void deleteListColorSize(List<ColorSizeProduct> listColorSize) {
		for (int i = 0; i < listColorSize.size(); i++) {
			colorSizeRepository.deleteById(listColorSize.get(i).getId());
		}
	}

	public void saveListColorSize(List<ColorSizeProductDto> listColorSizeDto, Product product) {
		for (int i = 0; i < listColorSizeDto.size(); i++) {
			listColorSizeDto.get(i).setProductId(product.getProductId());
			colorSizeRepository.save(convertColorSize.toEntity(listColorSizeDto.get(i)));
		}
	}

	public void saveSliderImage(ProductDto productDto, Product product) {
		ProductImageDto sliderImage = new ProductImageDto(productDto.getUrlImgs(), product.getProductId());
		if (productDto.getUrlImgId() != 0) {
			ProductImage oldProductImage = productImageRepository.getOne(productDto.getUrlImgId());
			productImageRepository.save(convertSliderImage.toEntity(sliderImage, oldProductImage));
		} else {
			productImageRepository.save(convertSliderImage.toEntity(sliderImage));
		}

	}

	@Override
	public ProductDto save(ProductDto productDto) {
		Product product = new Product();
		if (productDto.getProductId() != 0) {
			Product oldProduct = productRepository.getOne(productDto.getProductId());
			product = productRepository.save(convert.toEntity(productDto, oldProduct));
			saveSliderImage(productDto, product);
			deleteListColorSize(oldProduct.getColorSizeProducts());
			saveListColorSize(productDto.getListColorSizeDto(), product);

		} else {
			product = productRepository.save(convert.toEntity(productDto));
			saveSliderImage(productDto, product);
			saveListColorSize(productDto.getListColorSizeDto(), product);
		}
		return convert.toDto(product);
	}

	@Override
	public ProductDto edit(ProductDto productDto) {
		return null;
	}

	@Override
	public void remove(int productId) {
		productRepository.deleteById(productId);
	}

	@Override
	public Page<ProductDto> where(ProductFilter filter) {
		Page<ProductDto> listPagePrdDto = null;
		List<ProductDto> listPrdDto = new ArrayList<>();
		Sort sort = Sort.by(filter.getSort()).ascending();
		if (!filter.isAsc()) {
			sort = Sort.by(filter.getSort()).descending();
		}
		PageRequest pageRequest = PageRequest.of(filter.getPage(), filter.getPageSize(), sort);
		List<Product> listPrd = productRepository.findAll(new Specification<Product>() {
			private static final long serialVersionUID = 1L;

			@Override
			public Predicate toPredicate(Root<Product> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
				List<Predicate> predicates = new ArrayList<Predicate>();
				if (filter.getProductId() != 0) {
					predicates.add(criteriaBuilder.equal(root.get("productId"), filter.getProductId()));			
				}
				if (filter.getProductName() != null && !filter.getProductName().isEmpty()) {
					predicates.add(criteriaBuilder.like(root.get("productName"), "%" + filter.getProductName() + "%"));
				}
				if (filter.getPriceMax() != 0) {
					predicates.add(
							criteriaBuilder.between(root.get("price"), filter.getPriceMin(), filter.getPriceMax()));
				}
				if (filter.getCategoryPrdId() != 0) {
					predicates.add(criteriaBuilder.equal(root.get("categoryProduct").get("categoryId"),
							filter.getCategoryPrdId()));
				}
				if (filter.getSlugCategory() != null && !filter.getSlugCategory().isEmpty()) {
					predicates.add(
							criteriaBuilder.equal(root.get("categoryProduct").get("slug"), filter.getSlugCategory()));
				}
				if (filter.getColor() != null && !filter.getColor().isEmpty()) {			
//					filter.getColor().forEach(item -> {	
//						ListJoin<Product, ColorSizeProduct> ColorSizePrdJoin = root.join(Product_.colorSizeProducts);
//						Join<ColorSizeProduct, Color> ColorPrdJoin = ColorSizePrdJoin.join(ColorSizeProduct_.color);
//						Predicate predicateColor = criteriaBuilder.equal(ColorPrdJoin.get(Color_.colorName), item);
//						predicates.add(predicateColor);
//					});
					
					List<ColorSizeProduct> colorSizes = colorSizeRepository.findByColorColorNameIn(filter.getColor());
					List<Integer> _productIdByColor = colorSizes.stream().map(x -> x.getProductCSP().getProductId()).collect(Collectors.toList());
					predicates.add(root.get("productId").in(_productIdByColor));
				}
				if (filter.getSize() != null && !filter.getSize().isEmpty()) {
//					filter.getSize().forEach(item -> {
//						ListJoin<Product, ColorSizeProduct> ColorSizePrdJoin = root.join(Product_.colorSizeProducts);
//						Join<ColorSizeProduct, Size> ColorPrdJoin = ColorSizePrdJoin.join(ColorSizeProduct_.size);
//						Predicate predicateSize = criteriaBuilder.equal(ColorPrdJoin.get(Size_.sizeName), item);
//						predicates.add(predicateSize);
//					});
					
					List<ColorSizeProduct> colorSizes = colorSizeRepository.findBySizeSizeNameIn(filter.getSize());
					List<Integer> _productIdBySize = colorSizes.stream().map(x -> x.getProductCSP().getProductId()).collect(Collectors.toList());
					predicates.add(root.get("productId").in(_productIdBySize));

				}
				if (filter.getVote() != 0) {
					predicates.add(criteriaBuilder.equal(root.get("vote"), filter.getVote()));
				}
				if (filter.getBrand() != null && !filter.getBrand().isEmpty()) {
					predicates.add(criteriaBuilder.equal(root.get("brand"), filter.getBrand()));
				}
				if (filter.getFeatureProduct() != null) {
					predicates.add(criteriaBuilder.equal(root.get("featureProduct"), filter.getFeatureProduct()));
				}
				if (filter.getNewProduct() != null) {
					predicates.add(criteriaBuilder.equal(root.get("newProduct"), filter.getNewProduct()));
				}
				if (filter.getTrendProduct() != null) {
					predicates.add(criteriaBuilder.equal(root.get("trendProduct"), filter.getTrendProduct()));
				}
				if (filter.getStatus() != null) {
					predicates.add(criteriaBuilder.equal(root.get("status"), filter.getStatus()));
				}
				if (filter.getDiscount() != 0) {
					predicates.add(criteriaBuilder.equal(root.get("discount"), filter.getDiscount()));
				}
				return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
			}
		});

		if (listPrd != null) {
			listPrd.forEach(item -> {
				listPrdDto.add(convert.toDto(item));
			});
			List<ProductDto> distinctElements = listPrdDto.stream().filter(distinctByKey(x -> x.getProductId()))
					.collect(Collectors.toList());
			Collections.reverse(distinctElements);
			List<ProductDto> output = new ArrayList<>();
			int total = distinctElements.size();
			int start = (int) pageRequest.getOffset();
			int end = Math.min((start + pageRequest.getPageSize()), total);
			if (start <= end) {
				output = distinctElements.subList(start, end);
			}
			listPagePrdDto = new PageImpl<ProductDto>(output, pageRequest, total);
		}

		return listPagePrdDto;
	}

	private static <T> java.util.function.Predicate<T> distinctByKey(Function<? super T, Object> keyExtractor) {
		Map<Object, Boolean> map = new ConcurrentHashMap<>();
		return t -> map.putIfAbsent(keyExtractor.apply(t), Boolean.TRUE) == null;
	}


	@Override
	public ProductDto getProductDtoByName(String productName) {
		return convert.toDto(productRepository.findByProductName(productName));
	}

}
