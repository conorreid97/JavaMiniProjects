import { useEffect, useState } from 'react'
import reactLogo from './assets/react.svg'
import viteLogo from '/vite.svg'
import './App.css'
import ProductList from './Components/ProductList';
import CategoryFilter from './Components/CategoryFilter';

function App() {
  
  const [products, setProducts] = useState([]);
  const [categories, setCategories] = useState([]);

  const [selectedCategory, setSelectedCategory] = useState(null);
  const [searchTerm, setSearchTerm] = useState("");
  const [sortOrder, setSortOrder] = useState("asc");

  useEffect(() => {
    fetch('http://localhost:8080/api/products')
      .then(response => response.json())  // convert response to json
      .then(data => setProducts(data)); // setting the json to the product state
  
    fetch('http://localhost:8080/api/categories')
      .then(response => response.json())  // convert response to json
      .then(data => setCategories(data)); // setting the json to the product state
  }, []);

  const handleSearchChange = (event) => {
    setSearchTerm(event.target.value);
  };

  const handleSortChange = (event) => {
    setSortOrder(event.target.value);
  };

  const handleCategorySelect = (categoryId) => {
    setSelectedCategory(categoryId ? Number(categoryId) : null);
  };

  const filteredProducts = products
                .filter(product => {
                  return(
                    (selectedCategory ? product.category.id === selectedCategory : true)
                    &&
                    product.name.toLowerCase().includes(searchTerm.toLowerCase())
                  )
                })
                .sort((a, b) => {
                  if(sortOrder === "asc"){
                    return a.price - b.price;
                  } else{
                    return b.price - a.price;
                  }
                });
  return (
    <div className='containter'>
      <h1 className='my-4'>Product Catalog</h1>
      <div className='row align-items-center mb-4'>
        <div className='col-md-3 col-sm-12 mb-2'>
          <CategoryFilter categories={categories} onSelect={handleCategorySelect}/>
        </div>

        <div className='col-md-5 col-sm-12 mb-2'>
          <input
            type='text'
            className='form-control'
            placeholder='Search for products'
            onChange={handleSearchChange}
          />
        </div>

        <div className='col-md-4 col-sm-12 mb-2'>
          <select className='form-control' onChange={handleSortChange}>
            <option value="asc">Sort By Price: Low to High</option>
            <option value="desc">Sort By Price: High to Low</option>
          </select>
        </div>
      </div>

      <div>
        {filteredProducts.length ? (    // if there is products then do first brackets, if not do the second brackets
          // Display products
          <ProductList products ={filteredProducts}/>
        ) : (
          <p>No Product Found</p>
        )}
      </div>
    </div>
  )
}

export default App
