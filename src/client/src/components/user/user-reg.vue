<template>
    <div class="user_reg">
        <form @submit.prevent="Regist">
            <h4 class="user_reg__i">Регистрация</h4>
            <div class="row user_reg__i">
                <div class="col">
                    <label for="Name">Имя</label>
                    <input 
                        type="text" 
                        class="form-control" 
                        id="Name" 
                        v-model="user.firstName" 
                        required
                    >
                </div>
                <div class="col">
                    <label for="Lastname">Фамилия</label>
                    <input 
                        type="text" 
                        class="form-control" 
                        id="Lastname" 
                        v-model="user.secondName" 
                        required
                    >
                </div>
            </div>
            <div class="user_reg__i form-group">
                <label for="Email">Почта</label>
                <input 
                    type="email" 
                    class="form-control" 
                    id="Email" 
                    aria-describedby="emailHelp" 
                    placeholder="Enter email" 
                    v-model="user.email" 
                    required
                >
                <small 
                    id="emailHelp" 
                    class="form-text text-muted"
                >
                Мы никогда не передадим ваши личные данные кому-либо еще.
                </small>
            </div>
            <div class="user_reg__i form-group">
                <label for="exampleInputPassword1">Пароль</label>
                <input 
                    type="password" 
                    class="form-control" 
                    id="exampleInputPassword1" 
                    placeholder="Password" 
                    v-model="user.password" 
                    required
                >
            </div>
            <button 
                type="submit" 
                class="btn btn-primary" 
                style="margin-bottom: 10px"
            >
            Регистрация
            </button>
            <br>
            <RouterLink to="/login">Вход</RouterLink>
        </form>
    </div>
</template>

<script>
import axios from 'axios';

export default {
    name: 'v-user-reg',
    data() {
        return {
            user: {
                firstName: '',
                secondName: '',
                email: '', 
                password: '',
            },
            errors: []
        }
    },
    methods: {
        async Regist(e) {
            try {
                const user = await axios({
                    method: 'post',
                    url: 'http://localhost:8080/online-store/v1/signup',
                    data: {
                        firstName: this.user.firstName,
                        secondName: this.user.secondName,
                        email: this.user.email, 
                        password: this.user.password,
                    },
                    headers: {
                        "Content-type": "application/json; charset=UTF-8"
                    }
                    });
                alert('Регистрация прошла успешно')
                this.$router.push({name: 'login'})
                return user
            } catch (e) {
                if(e.response.status === 400) {
                    alert('Такой почты не существует!')
                }
                if(e.response.status === 409) {
                    alert('Такой пользователь уже существует!')
                }
                console.log(e)
            }
            e.preventDefault
        }
    }

}
</script>

<style lang="scss" scoped>
.user_reg{
    margin: auto;
    margin-top: 270px;
    width: 500px;
    padding: 20px 40px 20px 40px;
    border: 1px solid lightgrey;
    &__i {
        margin-bottom: 20px;
    }
}
</style>