import { createStore } from 'vuex'
import axios from 'axios'


const store = createStore({
    state: {
        error: 0,
        categories: [],
        products: [],
        cart: [],
        brdcrms: ['Главная']
    },
    mutations: {
        SET_PRODUCTS_TO_STATE: (state, products) => {
            state.products = products.sort((prod1, prod2) => prod1['price'] > prod2['price'] ? 1 : -1);
            state.error = 0 
        },
        SET_CATEGORIES_TO_STATE: (state, categories) => {
            state.categories = categories
        },
        SET_CART: (state, product) => {
            if (product.count > 0) {
                if (state.cart.length) {
                    let isProdExist = false;
                    state.cart.map(function(item) {
                        if (item.productId === product.productId) {
                            isProdExist = true;
                            item.quantity++;
                            item.count--;
                        }
                    })
                    if (!isProdExist) {
                        state.cart.push(product);
                        state.cart[state.cart.length - 1].count -= 1;
                        state.cart[state.cart.length - 1].quantity = 1;
                    }
                } else {
                    state.cart.push(product);
                    state.cart[state.cart.length - 1].count -= 1;
                    state.cart[state.cart.length - 1].quantity = 1;
                }
            }
            else { return 0 }
        },
        DELETE_PROD: (state, index) => {
            state.cart.splice(index, 1)
        },
        DECREMENT_ITEM: (state, index) => {
            if (state.cart[index].quantity > 1) {
                state.cart[index].quantity--
            }
            else if (state.cart[index].quantity === 1) {
                state.cart.splice(index, 1)
            }
        },
        INCREMENT_ITEM: (state, index) => {
            state.cart[index].quantity++
        },
        SET_BRDCRMS: (state, brdcrm) => {
            state.brdcrms.unshift(brdcrm)
        },
        CLEAR_BRDCRMS: (state) => {
            state.brdcrms = []
        },
        CLEAR_CART: (state, mail) => {
            localStorage.setItem(`${mail}_cart`, state.cart)
            state.cart = []
        },
        INSTALL_CART: (state, mail) => {
            if (localStorage.getItem(`${mail}_cart`)) {
                let userCart = JSON.parse(localStorage.getItem(`${mail}_cart`))
                for (let i in userCart) {
                    if (userCart[i].count > 0) {
                        if (state.cart.length) {
                            let isProdExist = false;
                            state.cart.map(function(item) {
                                if (item.productId === userCart[i].productId) {
                                    isProdExist = true;
                                    item.quantity++;
                                    item.count--;
                                }
                            })
                            if (!isProdExist) {
                                state.cart.push(userCart[i]);
                                state.cart[state.cart.length - 1].count -= 1;
                                state.cart[state.cart.length - 1].quantity = 1;
                            }
                        } else {
                            state.cart.push(userCart[i]);
                            state.cart[state.cart.length - 1].count -= 1;
                            state.cart[state.cart.length - 1].quantity = 1;
                        }
                    }
                }
            }
        },
        ERROR: (state, e) => {
            state.error = e
        }
    },
    actions: {
        async GET_PRODUCTS_FROM_API({commit}) {
            try {
                const products = await axios('http://localhost:8080/online-store/v1/products', {
                    method: "GET"
                });
                commit('SET_PRODUCTS_TO_STATE', products.data);
                return products;
            } catch (e) {
                console.log(e)
                return e;
            }
        },
        async GET_PRODUCTS_BY_CATEGORY({commit}, categoryId) {
            try {
                const products = await axios(`http://localhost:8080/online-store/v1/product_category/${categoryId}`, {
                    method: "GET"
                });
                commit('SET_PRODUCTS_TO_STATE', products.data);
                return products;
            } catch (e) {
                console.log(e);
                return e;
            }
        },
        async GET_CATEGORIES_FROM_API({commit}) {
            try {
                const categories = await axios('http://localhost:8080/online-store/v1/categories', { 
                    method: "GET"
                });
                commit('SET_CATEGORIES_TO_STATE', categories.data);
                return categories;
            } catch (e) {
                if(e.response.status === 404) {
                    commit('ERROR', 404)
                }
                return e;
            }
        },
        ADD_TO_CART({commit}, product) {
            commit('SET_CART', product);
        },
        DELETE_FROM_CART({commit}, index) {
            commit('DELETE_PROD', index)
        },
        DECREMENT_ITEM({commit}, index) {
            commit('DECREMENT_ITEM', index)
        },
        INCREMENT_ITEM({commit}, index) {
            commit('INCREMENT_ITEM', index)
        },
        SET_BRDCRMS({commit}, brdcrm) {
            commit('SET_BRDCRMS', brdcrm)
        },
        CLEAR_BRDCRMS({commit}) {
            commit('CLEAR_BRDCRMS')
        },
        SET_PRODUCTS_TO_STATE({commit}, products) {
            commit('SET_PRODUCTS_TO_STATE', products)
        },
        CLEAR_CART({commit}, email) {
            commit('CLEAR_CART', email)
        },
        INSTALL_CART({commit}, email) {
            commit('INSTALL_CART', email)
        }
    },
    getters: {
        PRODUCTS(state) {
            return state.products;
        },
        CART(state) {
            return state.cart;
        },
        EMAIL(state) {
            return state.email;
        }, 
        CATEGORIES(state) {
            return state.categories;
        },
        BRDCRMS(state) {
            return state.brdcrms;
        },
        ERROR(state) {
            return state.error;
        }
    }
});

export default store
