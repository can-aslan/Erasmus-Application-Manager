import { Axios, AxiosInstance } from "axios"
import { ResponsePreApprovalForm } from "../types/responseTypes"

export const getFile = async (axios: AxiosInstance, studentId: string) => {
    const response = await axios.get<ResponsePreApprovalForm>(`/fileService/form/${studentId}`)
    return response.data
}

// formData should contain the File object in the "file" property and FileType in the "fileType" property
export const submitFile = async (axios: AxiosInstance, formData: FormData, studentId: string) => {
    console.log(formData)
    const response = await axios.post(`/fileService/form/${studentId}`, formData, {
        headers: {
            "Content-Type": "multipart/form-data"
        }
    })
    return response.data
}

// TODO: Mutation: generate & download pre approval file
export const generateAndDownloadPreApproval = async (axios: AxiosInstance, studentId: string) => {
    const response = await axios.post(`/fileService/form/generate/download/${studentId}`)
    return response.data
}

// TODO: Mutation: generate & submit pre approval file
export const generateAndSubmitPreApproval = async (axios: AxiosInstance, studentId: string) => {
    const response = await axios.post(`/fileService/form/generate/submit/${studentId}`)
    return response.data
}

export const submitSignature = async (axios: AxiosInstance, formData: FormData, userId: string) => {
    const response = await axios.post(`/signature/user/${userId}`, formData, {
        headers: {
            "Content-Type": "multipart/form-data"
        }
    })
    return response.data
}

export const downloadSignature = async (axios: AxiosInstance, userId: string) => {
    const response = await axios.get(`signature/user/${userId}`)
    return response.data
}