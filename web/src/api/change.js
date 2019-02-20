import request from '@/utils/request'

export function C2Pcard(cardNumber_in, cardNumber_out, money, operateId) {
    return request({
        url: '/internalaccountchange/C2Pcard',
        method: 'post',
        data: {
            cardNumber_in,
            cardNumber_out,
            money,
            operateId
        }
    })
}
export function P2Ccard(cardNumber_in, cardNumber_out, money, operateId) {
    return request({
        url: '/internalaccountchange/P2Ccard',
        method: 'post',
        data: {
            cardNumber_in,
            cardNumber_out,
            money,
            operateId
        }
    })
}

export function qrcode() {
    return request({
        url: '/internalaccountchange/qrcode',
        method: 'post',
        data: {
            cardNumber,
            loginId,
            money,
            operateId
        }
    })
}